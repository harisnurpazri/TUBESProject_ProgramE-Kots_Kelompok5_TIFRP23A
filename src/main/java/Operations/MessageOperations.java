package Operations;

import com.mycompany.electronic_kostprogram.DatabaseConnection;
import model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageOperations {
    private Connection connection;

    public MessageOperations() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }
    public List<Message> getMessagesByTenantId(int tenantId) throws SQLException {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM messages WHERE ID_Tenant = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, tenantId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idMessage = resultSet.getInt("ID_Message");
                int idTenant = resultSet.getInt("ID_Tenant");
                String name = resultSet.getString("Name");
                String message = resultSet.getString("Message");
                messages.add(new Message(idMessage, idTenant, name, message));
            }
        }
        return messages;
    }

    public List<Message> getAllMessages() throws SQLException {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM messages";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idMessage = resultSet.getInt("ID_Message");
                int idTenant = resultSet.getInt("ID_Tenant");
                String name = resultSet.getString("Name");
                String message = resultSet.getString("Message");
                messages.add(new Message(idMessage, idTenant, name, message));
            }
        }
        return messages;
    }
    public int getTenantIdByName(String tenantName) throws SQLException {
        String query = "SELECT ID_Tenant FROM tenant_info WHERE Name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, tenantName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ID_Tenant");
            }
        }
        return -1; // Tenant tidak ditemukan
    }


    public void addMessage(Message message) throws SQLException {
        // Periksa apakah ID_Tenant ada di tenant_info
        String checkTenantQuery = "SELECT COUNT(*) FROM tenant_info WHERE ID_Tenant = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkTenantQuery)) {
            checkStmt.setInt(1, message.getIdTenant());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new SQLException("ID_Tenant " + message.getIdTenant() + " tidak ditemukan di tenant_info.");
            }
        }

        // Jika ID_Tenant valid, tambahkan pesan
        String query = "INSERT INTO messages (ID_Tenant, Name, Message) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, message.getIdTenant());
            statement.setString(2, message.getName());
            statement.setString(3, message.getMessage());
            statement.executeUpdate();
        }
    }


    public void deleteMessage(int idMessage) throws SQLException {
        String query = "DELETE FROM messages WHERE ID_Message = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idMessage);
            statement.executeUpdate();
        }
    }
}
