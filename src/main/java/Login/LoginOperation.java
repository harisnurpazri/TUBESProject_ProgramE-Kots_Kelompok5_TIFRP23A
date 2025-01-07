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

    // Validasi Login
    public String validateLogin(String username, String password) {
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            return "admin";
        }
        return validateTenant(username, password);
    }

    // Validasi Penghuni (User Default)
    private String validateTenant(String username, String password) {
        String query = "SELECT username FROM account WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "user"; // Role default penghuni
            }
        } catch (SQLException e) {
            System.err.println("Error validating tenant login.");
            e.printStackTrace();
        }
        return null;
    }

    // Tambah Akun Penghuni
    public void addUser(int id_account, String name, String username, String password) {
        String query = "INSERT INTO account (id_account, name, username, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id_account);
            stmt.setString(2, name);
            stmt.setString(3, username);
            stmt.setString(4, password);
            stmt.executeUpdate();
            System.out.println("Pengguna baru berhasil ditambahkan ke tabel 'account'!");
        } catch (SQLException e) {
            System.err.println("Gagal menambahkan pengguna ke tabel 'account'.");
            e.printStackTrace();
        }
    }
}
