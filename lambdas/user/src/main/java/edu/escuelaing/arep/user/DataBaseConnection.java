/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.escuelaing.arep.user;

/**
 *
 * @author maritzamonsalvebautista
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static final String URL = "jdbc:mysql://98.90.201.126:3306/twitter?createDatabaseIfNotExist=true";
    private static final String USER = "root";
    private static final String PASSWORD = "secret-twitter";

    private static Connection connection;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}
