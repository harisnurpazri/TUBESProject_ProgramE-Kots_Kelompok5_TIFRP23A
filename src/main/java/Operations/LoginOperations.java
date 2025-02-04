package Operations;

import model.Login;
import com.mycompany.electronic_kostprogram.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginOperations {
    private Connection connection;

    public LoginOperations() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public boolean authenticate(String username, String password) throws SQLException {
        String query = "SELECT * FROM login WHERE Username = ? AND Password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public String getRole(String username) throws SQLException {
        String query = "SELECT Role FROM login WHERE Username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("Role");
                }
            }
        }
        return null;
    }

    public Login getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM login WHERE Username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int idAccount = resultSet.getInt("ID_Account");
                    String name = resultSet.getString("Name");
                    String password = resultSet.getString("Password");
                    String role = resultSet.getString("Role");
                    return new Login(idAccount, name, username, password, role);
                }
            }
        }
        return null;
    }

    public List<Login> getAllLogins() throws SQLException {
        List<Login> logins = new ArrayList<>();
        String query = "SELECT * FROM login";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int idAccount = resultSet.getInt("ID_Account");
                String name = resultSet.getString("Name");
                String username = resultSet.getString("Username");
                String password = resultSet.getString("Password");
                String role = resultSet.getString("Role");
                logins.add(new Login(idAccount, name, username, password, role));
            }
        }
        return logins;
    }

    public void addLogin(Login login) throws SQLException {
        String query = "INSERT INTO login (Name, Username, Password, Role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login.getName());
            preparedStatement.setString(2, login.getUsername());
            preparedStatement.setString(3, login.getPassword());
            preparedStatement.setString(4, login.getRole());
            preparedStatement.executeUpdate();
        }
    }

    public void updateLogin(Login login) throws SQLException {
        String query = "UPDATE login SET Name = ?, Username = ?, Password = ?, Role = ? WHERE ID_Account = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login.getName());
            preparedStatement.setString(2, login.getUsername());
            preparedStatement.setString(3, login.getPassword());
            preparedStatement.setString(4, login.getRole());
            preparedStatement.setInt(5, login.getIdAccount());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteLogin(int idAccount) throws SQLException {
        String query = "DELETE FROM login WHERE ID_Account = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idAccount);
            preparedStatement.executeUpdate();
        }
    }
}
