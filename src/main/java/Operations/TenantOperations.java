package Operations;

import com.mycompany.electronic_kostprogram.DatabaseConnection;
import model.Tenant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TenantOperations {
    private Connection connection;

    public TenantOperations() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Tenant> getAllTenants() throws SQLException {
        String query = "SELECT * FROM tenant_info";
        List<Tenant> tenants = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                tenants.add(new Tenant(
                    resultSet.getInt("ID_Tenant"),
                    resultSet.getString("Name"),
                    resultSet.getString("Phone_Number"),
                    resultSet.getString("Address"),
                    resultSet.getInt("ID_Room"),
                    resultSet.getDouble("Room_Price"),
                    resultSet.getDate("Check_In_Date"),
                    resultSet.getDate("Pay_Date")
                ));
            }
        }
        return tenants;
    }

    public void addTenant(Tenant tenant) throws SQLException {
        String query = "INSERT INTO tenant_info (Name, Phone_Number, Address, ID_Room, Room_Price, Check_In_Date, Pay_Date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, tenant.getName());
            preparedStatement.setString(2, tenant.getPhoneNumber());
            preparedStatement.setString(3, tenant.getAddress());
            preparedStatement.setInt(4, tenant.getIdRoom());
            preparedStatement.setDouble(5, tenant.getRoomPrice());
            preparedStatement.setDate(6, tenant.getCheckInDate());
            preparedStatement.setDate(7, tenant.getPayDate());
            preparedStatement.executeUpdate();
        }
    }

    public void updateTenant(Tenant tenant) throws SQLException {
        String query = "UPDATE tenant_info SET Name = ?, Phone_Number = ?, Address = ?, ID_Room = ?, Room_Price = ?, Check_In_Date = ?, Pay_Date = ? WHERE ID_Tenant = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, tenant.getName());
            preparedStatement.setString(2, tenant.getPhoneNumber());
            preparedStatement.setString(3, tenant.getAddress());
            preparedStatement.setInt(4, tenant.getIdRoom());
            preparedStatement.setDouble(5, tenant.getRoomPrice());
            preparedStatement.setDate(6, tenant.getCheckInDate());
            preparedStatement.setDate(7, tenant.getPayDate());
            preparedStatement.setInt(8, tenant.getIdUser());
            preparedStatement.executeUpdate();
        }
    }

    public void deleteTenant(int idTenant) throws SQLException {
        String query = "DELETE FROM tenant_info WHERE ID_Tenant = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idTenant);
            preparedStatement.executeUpdate();
        }
    }

    public Tenant getTenantById(int idTenant) throws SQLException {
        String query = "SELECT * FROM tenant_info WHERE ID_Tenant = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idTenant);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Tenant(
                    resultSet.getInt("ID_Tenant"),
                    resultSet.getString("Name"),
                    resultSet.getString("Phone_Number"),
                    resultSet.getString("Address"),
                    resultSet.getInt("ID_Room"),
                    resultSet.getDouble("Room_Price"),
                    resultSet.getDate("Check_In_Date"),
                    resultSet.getDate("Pay_Date")
                );
            }
        }
        return null;
    }
    
    public void updateTenantPayDate(int idTenant, java.sql.Date PayDate) throws SQLException {
        String query = "UPDATE tenant_info SET Pay_Date = ? WHERE ID_Tenant = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, PayDate);
            statement.setInt(2, idTenant);
            statement.executeUpdate();
        }
    }
}
