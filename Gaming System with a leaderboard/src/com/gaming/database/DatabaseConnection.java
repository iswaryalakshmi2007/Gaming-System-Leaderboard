package com.gaming.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/gaming_system";

    private static final String USER = "root";

    private static final String PASSWORD = "YOUR PASSWORD";

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            throw new SQLException("JDBC Driver Error", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}