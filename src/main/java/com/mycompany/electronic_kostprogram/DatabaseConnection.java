package com.mycompany.electronic_kostprogram;

import java.sql.Connection; // Mengimpor kelas Connection dari pustaka JDBC
import java.sql.DriverManager; // Mengimpor kelas DriverManager dari pustaka JDBC
import java.sql.SQLException; // Mengimpor kelas SQLException dari pustaka JDBC

public class DatabaseConnection {
    // URL koneksi database MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/training_kost";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Variabel statis untuk menyimpan objek Connection
    private static Connection connection;

    // Metode untuk mendapatkan koneksi database
    public static Connection getConnection() {
        // Jika koneksi belum dibuat, buat koneksi baru
        if (connection == null) {
            try {
                // Membuat koneksi ke database menggunakan URL, username, dan password
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                // Menampilkan stack trace jika terjadi kesalahan SQL
                e.printStackTrace();
            }
        }
        // Mengembalikan objek Connection
        return connection;
    }
}
