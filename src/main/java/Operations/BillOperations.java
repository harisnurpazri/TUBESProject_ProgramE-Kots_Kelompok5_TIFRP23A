package Operations;

import com.mycompany.electronic_kostprogram.DatabaseConnection;
import model.Bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillOperations {
    private Connection connection;
    private TenantOperations tenantOperations;

    public BillOperations() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
        this.tenantOperations = new TenantOperations();
    }

    public List<Bill> getAllBills() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM bills";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int idTransaction = resultSet.getInt("ID_Transaction");
                int idTenant = resultSet.getInt("ID_Tenant");
                String name = resultSet.getString("Name");
                int idRoom = resultSet.getInt("ID_Room");
                double roomPrice = resultSet.getDouble("Room_Price");
                java.sql.Date paymentDate = resultSet.getDate("Payment_Date");
                double amountPaid = resultSet.getDouble("Amount_Paid");
                bills.add(new Bill(idTransaction, idTenant, name, idRoom, roomPrice, paymentDate, amountPaid));
            }
        }
        return bills;
    }

    public void addBill(Bill bill) throws SQLException {
        String query = "INSERT INTO bills (ID_Tenant, Name, ID_Room, Room_Price, Payment_Date, Amount_Paid) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bill.getIdTenant());
            statement.setString(2, bill.getName());
            statement.setInt(3, bill.getIdRoom());
            statement.setDouble(4, bill.getRoomPrice());
            statement.setDate(5, bill.getPaymentDate());
            statement.setDouble(6, bill.getAmountPaid());
            statement.executeUpdate();
        }
    }

    public void updateBill(Bill bill) throws SQLException {
        String query = "UPDATE bills SET ID_Tenant = ?, Name = ?, ID_Room = ?, Room_Price = ?, Payment_Date = ?, Amount_Paid = ? WHERE ID_Transaction = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bill.getIdTenant());
            statement.setString(2, bill.getName());
            statement.setInt(3, bill.getIdRoom());
            statement.setDouble(4, bill.getRoomPrice());
            statement.setDate(5, bill.getPaymentDate());
            statement.setDouble(6, bill.getAmountPaid());
            statement.setInt(7, bill.getIdTransaction());
            statement.executeUpdate();
        }
    }

    public void deleteBill(int idTransaction) throws SQLException {
        String query = "DELETE FROM bills WHERE ID_Transaction = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idTransaction);
            statement.executeUpdate();
        }
    }
}
