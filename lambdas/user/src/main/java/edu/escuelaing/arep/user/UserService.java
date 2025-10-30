package edu.escuelaing.arep.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    public static User createUser(User user) throws SQLException, ClassNotFoundException {
        if (user.geName() == null || user.geName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        Connection conn = DataBaseConnection.getConnection();
        String sql = "INSERT INTO user (name, password) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, user.geName());
        stmt.setString(2, user.getPassword());
        int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }

        // Aseguramos que el nombre no sea nulo al devolver el objeto
        if (user.geName() == null) {
            throw new SQLException("User name should not be null after creation.");
        }

        return user;
    }

    public static List<User> getAllUsers() throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "SELECT * FROM user";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        List<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(new User(rs.getLong("id"), rs.getString("name"), rs.getString("password")));
        }
        return users;
    }

    public static User getUserById(Long id) throws SQLException, ClassNotFoundException {
        Connection conn = DataBaseConnection.getConnection();
        String sql = "SELECT * FROM user WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new User(rs.getLong("id"), rs.getString("name"), rs.getString("password"));
        }
        return null;
    }
}
