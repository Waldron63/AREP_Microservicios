package edu.escuelaing.arep.stream;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StreamService {

    // Obtener todos los streams con sus posts
    public static List<Stream> getAllStreams() throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "SELECT s.id AS stream_id, s.name AS stream_name, sp.posts_id " +
                "FROM stream s LEFT JOIN stream_posts sp ON s.id = sp.stream_id " +
                "ORDER BY s.id";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        List<Stream> streams = new ArrayList<>();
        Stream currentStream = null;
        Long currentId = null;

        while (rs.next()) {
            Long streamId = rs.getLong("stream_id");

            if (currentId == null || !currentId.equals(streamId)) {
                // Si es un nuevo stream, crea un nuevo objeto
                if (currentStream != null) {
                    streams.add(currentStream);
                }
                currentStream = new Stream();
                currentStream.setId(streamId);
                currentStream.setName(rs.getString("stream_name"));
                currentStream.setPosts(new ArrayList<>());
                currentId = streamId;
            }

            Long postId = rs.getLong("posts_id");
            if (!rs.wasNull()) {
                currentStream.getPosts().add(postId);
            }
        }

        if (currentStream != null) {
            streams.add(currentStream);
        }

        return streams;
    }

    // Obtener un stream por ID con su lista de posts
    public static Stream getStreamById(Long id) throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "SELECT s.id AS stream_id, s.name AS stream_name, sp.posts_id " +
                "FROM stream s LEFT JOIN stream_posts sp ON s.id = sp.stream_id " +
                "WHERE s.id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();

        Stream stream = null;

        while (rs.next()) {
            if (stream == null) {
                stream = new Stream();
                stream.setId(rs.getLong("stream_id"));
                stream.setName(rs.getString("stream_name"));
                stream.setPosts(new ArrayList<>());
            }

            Long postId = rs.getLong("posts_id");
            if (!rs.wasNull()) {
                stream.getPosts().add(postId);
            }
        }

        return stream;
    }

    // Crear un nuevo stream
    public static Stream createStream(Stream stream) throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "INSERT INTO stream (name) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, stream.getName());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        Long id = null;
        if (rs.next()) {
            id = rs.getLong(1);
        }

        return new Stream(id, stream.getName(), new ArrayList<>());
    }

    // Agregar un post a un stream
    public static Stream addPostToStream(AddPostRequest addPostRequest) throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "INSERT INTO stream_posts (posts_id, stream_id) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, addPostRequest.getPostId());
        stmt.setLong(2, addPostRequest.getStreamId());
        stmt.executeUpdate();
        return  getStreamById(addPostRequest.getStreamId());
    }
}
