
package com.mycompany.electronic_kostprogram;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/tryprogram";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static boolean addTenant(String name, String username, String password) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); 
             PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO penghuni_kost (NamaPenghuni, username, password) VALUES (?, ?, ?)")) {
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

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); 
             PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM penghuni_kost WHERE username = ? AND password = ?")) {
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
