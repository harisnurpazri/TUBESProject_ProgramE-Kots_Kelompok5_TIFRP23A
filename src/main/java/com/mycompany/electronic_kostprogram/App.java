package com.mycompany.electronic_kostprogram;

import view.LoginView; // Mengimpor kelas LoginView dari paket view
import javafx.application.Application; // Mengimpor kelas Application dari JavaFX
import javafx.scene.Scene; // Mengimpor kelas Scene dari JavaFX
import javafx.stage.Stage; // Mengimpor kelas Stage dari JavaFX

public class App extends Application {

    // Metode start yang dijalankan saat aplikasi dimulai
    @Override
    public void start(Stage primaryStage) {
        // Membuat objek LoginView dan meneruskan primaryStage sebagai parameter
        LoginView loginView = new LoginView(primaryStage);
        
        // Membuat objek Scene dengan ukuran 400x300 piksel dan menambahkan komponen dari LoginView
        Scene scene = new Scene(loginView.getView(), 400, 300);
        
        // Mengatur judul jendela utama aplikasi
        primaryStage.setTitle("Kost Management App");
        
        // Menetapkan scene sebagai tampilan utama dari primaryStage
        primaryStage.setScene(scene);
        
        // Menampilkan jendela utama aplikasi
        primaryStage.show();
    }

    // Metode main yang merupakan titik masuk dari aplikasi
    public static void main(String[] args) {
        // Memulai siklus hidup aplikasi JavaFX dengan memanggil metode launch
        launch(args);
    }
}
