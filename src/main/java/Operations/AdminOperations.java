package Operations;

import model.Admin; // Mengimpor kelas Admin dari paket model
import com.mycompany.electronic_kostprogram.DatabaseConnection; // Mengimpor kelas DatabaseConnection dari paket com.mycompany.projecttubespbo

import java.sql.*; // Mengimpor kelas-kelas JDBC
import java.util.ArrayList; // Mengimpor kelas ArrayList dari pustaka Java
import java.util.List; // Mengimpor kelas List dari pustaka Java

public class AdminOperations {
    // Variabel untuk menyimpan koneksi database
    private Connection connection;

    // Konstruktor untuk menginisialisasi koneksi database
    public AdminOperations() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    // Metode untuk mendapatkan semua admin dari database
    public List<Admin> getAllAdmins() throws SQLException {
        List<Admin> admins = new ArrayList<>(); // Membuat list untuk menyimpan objek Admin
        String query = "SELECT * FROM admin_info"; // Query SQL untuk mengambil semua data dari tabel admin_info
        try (Statement statement = connection.createStatement(); // Membuat objek Statement
             ResultSet resultSet = statement.executeQuery(query)) { // Menjalankan query dan mendapatkan ResultSet
            while (resultSet.next()) { // Mengulangi setiap baris dalam ResultSet
                int idAdmin = resultSet.getInt("ID_Admin"); // Mendapatkan nilai ID_Admin dari baris saat ini
                String kostAddress = resultSet.getString("Kost_Address"); // Mendapatkan nilai Kost_Address dari baris saat ini
                String accountName = resultSet.getString("Account_Name"); // Mendapatkan nilai Account_Name dari baris saat ini
                String accountNumber = resultSet.getString("Account_Number"); // Mendapatkan nilai Account_Number dari baris saat ini
                admins.add(new Admin(idAdmin, kostAddress, accountName, accountNumber)); // Menambahkan objek Admin ke list
            }
        }
        return admins; // Mengembalikan list admin
    }

    // Metode untuk menambahkan admin baru ke database
    public void addAdmin(Admin admin) throws SQLException {
        String query = "INSERT INTO admin_info (Kost_Address, Account_Name, Account_Number) VALUES (?, ?, ?)"; // Query SQL untuk menambahkan data
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) { // Membuat objek PreparedStatement
            preparedStatement.setString(1, admin.getkostAddress()); // Mengatur nilai parameter pertama (Kost_Address)
            preparedStatement.setString(2, admin.getaccountName()); // Mengatur nilai parameter kedua (Account_Name)
            preparedStatement.setString(3, admin.getaccountNumber()); // Mengatur nilai parameter ketiga (Account_Number)
            preparedStatement.executeUpdate(); // Menjalankan query untuk menambahkan data
        }
    }

    // Metode untuk memperbarui informasi admin di database
    public void updateAdmin(Admin admin) throws SQLException {
        String query = "UPDATE admin_info SET Kost_Address = ?, Account_Name = ?, Account_Number = ? WHERE ID_Admin = ?"; // Query SQL untuk memperbarui data
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) { // Membuat objek PreparedStatement
            preparedStatement.setString(1, admin.getkostAddress()); // Mengatur nilai parameter pertama (Kost_Address)
            preparedStatement.setString(2, admin.getaccountName()); // Mengatur nilai parameter kedua (Account_Name)
            preparedStatement.setString(3, admin.getaccountNumber()); // Mengatur nilai parameter ketiga (Account_Number)
            preparedStatement.setInt(4, admin.getIdUser()); // Mengatur nilai parameter keempat (ID_Admin)
            preparedStatement.executeUpdate(); // Menjalankan query untuk memperbarui data
        }
    }

    // Metode untuk menghapus admin dari database
    public void deleteAdmin(int idAdmin) throws SQLException {
        String query = "DELETE FROM admin_info WHERE ID_Admin = ?"; // Query SQL untuk menghapus data
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) { // Membuat objek PreparedStatement
            preparedStatement.setInt(1, idAdmin); // Mengatur nilai parameter pertama (ID_Admin)
            preparedStatement.executeUpdate(); // Menjalankan query untuk menghapus data
        }
    }
}
