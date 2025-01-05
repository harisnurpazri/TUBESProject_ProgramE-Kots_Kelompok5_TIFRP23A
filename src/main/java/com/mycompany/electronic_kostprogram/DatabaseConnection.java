
package com.mycompany.electronic_kostprogram;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.*;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/tryprogram";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    static {
        try {
            // Memuat driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Membuat koneksi ke database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Failed to close database connection.");
                e.printStackTrace();
            }
        }
    }

    public static boolean addTenant(String name, String username, String password) {
        String query = "INSERT INTO penghuni_kost (NamaPenghuni, username, password) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            System.out.println("Tenant added: " + username);
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding tenant: " + e.getMessage());
            return false;
        }
    }

    public static User authenticate(String username, String password) {
        if ("admin".equals(username) && "admin".equals(password)) {
            return new Admin(username, password);
        }

        String query = "SELECT * FROM penghuni_kost WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("Tenant authenticated: " + username);
                return new Tenant(username, password);
            }
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        }

        return null;
    }
}

