package login;

import com.mycompany.electronic_kostprogram.DatabaseConnection;
import java.sql.*;

public class LoginOperation {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private Connection connection;

    public LoginOperation() throws SQLException {
        connection = DatabaseConnection.getConnection();
        if (connection == null) {
            throw new SQLException("Failed to connect to database.");
        }
    }

    public String validateLogin(String username, String password) {
        // Logika khusus untuk admin login
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            return "admin"; // Admin login berhasil
        }

        // Validasi untuk pengguna biasa di database
        return validateUser(username, password);
    }

    public String validateUser(String username, String password) {
        if (isUsernameValid(username) && isPasswordValid(username, password)) {
            return getRole(username);
        }
        return null;
    }

    public boolean isUsernameValid(String username) {
        String query = "SELECT username FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPasswordValid(String username, String password) {
        String query = "SELECT password FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getRole(String username) {
        String query = "SELECT role FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUser(String username, String password) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, 'user')";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
