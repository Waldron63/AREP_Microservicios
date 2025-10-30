package edu.escuelaing.arep.post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostService {

    public static Post createPost(Post post) throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "INSERT INTO post (message, likes, usuario_id) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, post.getMessage());
        stmt.setInt(2, 0);
        stmt.setLong(3, post.getUser());
        int affectedRows = stmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating post failed, no rows affected.");
        }
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                post.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating post failed, no ID obtained.");
            }
        }
        return post;
    }


    public static List<Post> getAllPosts() throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "SELECT * FROM post";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        List<Post> posts = new ArrayList<>();
        while (rs.next()) {
            posts.add(new Post(rs.getLong("id"), rs.getString("message"), rs.getInt("likes"),rs.getLong("usuario_id")));
        }
        return posts;
    }

    public static Post getPostById(Long id) throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "SELECT * FROM post WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Post(rs.getLong("id"), rs.getString("message"), rs.getInt("likes"),rs.getLong("usuario_id"));
        }
        return null;
    }

    public static Post updatePost(Long id) throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "UPDATE post SET likes = ? WHERE id = ?";
        Post post = getPostById(id);
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, post.getLikes()+1);
        stmt.setLong(2, id);
        stmt.executeUpdate();
        return getPostById(id);

    }

    public static boolean deletePost(Long id) throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "DELETE FROM post WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, id);
        stmt.executeUpdate();
        if (getPostById(id) == null){
            return true;
        }
        return false;
    }
}

