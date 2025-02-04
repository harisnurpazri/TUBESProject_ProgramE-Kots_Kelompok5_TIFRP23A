package view;

import Operations.LoginOperations;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.effect.GaussianBlur;

import java.io.InputStream;
import java.sql.SQLException;

public class LoginView {
    private Stage primaryStage;

    // Konstruktor yang menerima objek Stage dan menyimpannya dalam variabel primaryStage
    public LoginView(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Metode yang mengembalikan objek StackPane yang berisi elemen-elemen antarmuka pengguna login
    public StackPane getView() {
        StackPane stackPane = new StackPane();

        // Memuat gambar background dari file bg.jpg
        InputStream backgroundStream = getClass().getResourceAsStream("/images/bg.jpg");
        if (backgroundStream == null) {
            throw new RuntimeException("Background image not found!");
        }
        ImageView backgroundImageView = new ImageView(new Image(backgroundStream));
        backgroundImageView.setFitWidth(1366); // Mengatur lebar gambar background
        backgroundImageView.setFitHeight(768); // Mengatur tinggi gambar background
        backgroundImageView.setEffect(new GaussianBlur(8)); // Menambahkan efek blur pada gambar background

        // Membuat VBox untuk menampung elemen-elemen login dengan jarak vertikal 20
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(50)); // Menambahkan padding 50 ke VBox
        vbox.setAlignment(Pos.CENTER); // Menyusun elemen-elemen di tengah VBox
        vbox.setStyle("-fx-background-color: rgba(180, 180, 180, 0.1);"); // Memberikan latar belakang transparan pada VBox

        // Memuat gambar logo dari file logo.png
        InputStream logoStream = getClass().getResourceAsStream("/images/logo.png");
        if (logoStream == null) {
            throw new RuntimeException("Logo image not found!");
        }
        ImageView logoImageView = new ImageView(new Image(logoStream));
        logoImageView.setPreserveRatio(true); // Menjaga rasio aspek gambar logo
        logoImageView.setFitHeight(120); // Mengatur tinggi gambar logo
        VBox.setMargin(logoImageView, new Insets(0, 0, 25, 0)); // Menambahkan margin bawah 25 ke gambar logo

        // Membuat label "WELCOME" dengan font size 20 dan bold
        Label welcomeLabel = new Label("WELCOME");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Membuat VBox untuk menampung form login dengan jarak vertikal 10
        VBox formContainer = new VBox(10);
        formContainer.setPadding(new Insets(20)); // Menambahkan padding 20 ke formContainer
        formContainer.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 0);"); // Memberikan latar belakang putih dengan radius 10 dan efek shadow
        formContainer.setPrefSize(400, 300); // Mengatur ukuran preferensi formContainer menjadi 400x300
        formContainer.setMaxSize(400, 300); // Mengatur ukuran maksimum formContainer menjadi 400x300
        formContainer.setAlignment(Pos.CENTER); // Menyusun elemen-elemen di tengah formContainer
        formContainer.getChildren().addAll(logoImageView, welcomeLabel); // Menambahkan gambar logo dan label welcome ke formContainer

        // Membuat input username
        InputStream usernameStream = getClass().getResourceAsStream("/images/user.png");
        if (usernameStream == null) {
            throw new RuntimeException("Username icon not found!");
        }
        ImageView usernameIcon = new ImageView(new Image(usernameStream));
        usernameIcon.setFitHeight(20); // Mengatur tinggi ikon username
        usernameIcon.setFitWidth(20); // Mengatur lebar ikon username

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username"); // Menambahkan teks petunjuk "Username" pada TextField
        usernameField.setStyle("-fx-background-radius: 5; -fx-padding: 10 10 10 35;"); // Memberikan radius 5 dan padding pada TextField
        usernameField.setPrefHeight(35); // Mengatur tinggi preferensi TextField menjadi 35

        StackPane usernameStack = new StackPane();
        usernameStack.getChildren().addAll(usernameField, usernameIcon); // Menambahkan TextField dan ikon username ke StackPane
        StackPane.setAlignment(usernameIcon, Pos.CENTER_LEFT); // Menyusun ikon username di tengah kiri StackPane
        StackPane.setMargin(usernameIcon, new Insets(0, 0, 0, 10)); // Menambahkan margin kiri 10 ke ikon username

        // Membuat input password
        InputStream passwordStream = getClass().getResourceAsStream("/images/padlock.png");
        if (passwordStream == null) {
            throw new RuntimeException("Password icon not found!");
        }
        ImageView passwordIcon = new ImageView(new Image(passwordStream));
        passwordIcon.setFitHeight(20); // Mengatur tinggi ikon password
        passwordIcon.setFitWidth(20); // Mengatur lebar ikon password

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password"); // Menambahkan teks petunjuk "Password" pada PasswordField
        passwordField.setStyle("-fx-background-radius: 5; -fx-padding: 10 10 10 35;"); // Memberikan radius 5 dan padding pada PasswordField
        passwordField.setPrefHeight(35); // Mengatur tinggi preferensi PasswordField menjadi 35
        usernameField.setPrefWidth(250); // Mengatur lebar preferensi TextField menjadi 250

        StackPane passwordStack = new StackPane();
        passwordStack.getChildren().addAll(passwordField, passwordIcon); // Menambahkan PasswordField dan ikon password ke StackPane
        StackPane.setAlignment(passwordIcon, Pos.CENTER_LEFT); // Menyusun ikon password di tengah kiri StackPane
        StackPane.setMargin(passwordIcon, new Insets(0, 0, 0, 10)); // Menambahkan margin kiri 10 ke ikon password

        // Membuat tombol login
        Button loginButton = new Button("LOGIN");
        loginButton.setStyle("-fx-background-color: #007BFF; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 5; -fx-padding: 5 50 ;"); // Memberikan gaya pada tombol login
        loginButton.setOnAction(e -> login(usernameField.getText(), passwordField.getText())); // Menambahkan aksi ketika tombol login ditekan

        VBox.setMargin(loginButton, new Insets(15, 0, 0, 0)); // Menambahkan margin atas 15 ke tombol login

        formContainer.getChildren().addAll(usernameStack, passwordStack, loginButton); // Menambahkan StackPane username, password, dan tombol login ke formContainer
        vbox.getChildren().add(formContainer); // Menambahkan formContainer ke VBox

        stackPane.getChildren().addAll(backgroundImageView, vbox); // Menambahkan gambar background dan VBox ke StackPane

        return stackPane; // Mengembalikan StackPane yang berisi elemen-elemen login
    }

    // Metode untuk melakukan autentikasi pengguna
    private void login(String username, String password) {
        try {
            LoginOperations loginOperations = new LoginOperations();
            if (loginOperations.authenticate(username, password)) { // Memeriksa apakah autentikasi berhasil
                String role = loginOperations.getRole(username); // Mendapatkan peran pengguna
                if (role.equals("Admin")) {
                    AdminDashboard adminDashboard = new AdminDashboard(primaryStage);
                    Scene scene = new Scene(adminDashboard.getView(), 800, 600); // Membuat Scene dengan ukuran 800x600
                    primaryStage.setTitle("Dashboard Admin"); // Mengatur judul Stage menjadi "Dashboard Admin"
                    primaryStage.setScene(scene); // Mengatur Scene baru ke Stage
                    primaryStage.show(); // Menampilkan Stage
                } else if (role.equals("Tenant")) {
                    String tenantName = loginOperations.getUserByUsername(username).getName(); // Mendapatkan nama penghuni
                    TenantDashboard tenantDashboard = new TenantDashboard(primaryStage, tenantName);
                    Scene scene = new Scene(tenantDashboard.getView(), 800, 600); // Membuat Scene dengan ukuran 800x600
                    primaryStage.setTitle("Dashboard Penghuni"); // Mengatur judul Stage menjadi "Dashboard Penghuni"
                    primaryStage.setScene(scene); // Mengatur Scene baru ke Stage
                    primaryStage.show(); // Menampilkan Stage
                }
            } else {
                showAlert("Error", "Username atau password tidak valid"); // Menampilkan alert jika autentikasi gagal
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Mencetak stack trace jika terjadi SQLException
            showAlert("Error", "Gagal melakukan autentikasi"); // Menampilkan alert jika terjadi kesalahan SQL
        }
    }

    // Metode untuk menampilkan alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title); // Mengatur judul alert
        alert.setHeaderText(null); // Mengosongkan header text alert
        alert.setContentText(message); // Mengatur pesan alert
        alert.showAndWait(); // Menampilkan alert dan menunggu hingga alert ditutup
    }
}
