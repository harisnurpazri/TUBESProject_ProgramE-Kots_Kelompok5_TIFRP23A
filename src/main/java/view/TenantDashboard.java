package view;

import Operations.BillOperations;
import Operations.MessageOperations;
import Operations.RoomOperations;
import Operations.TenantOperations;
import Operations.AdminOperations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Bill;
import model.Message;
import model.Admin;

import java.sql.SQLException;
import java.util.List;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class TenantDashboard {
    private Stage primaryStage;
    private TenantOperations tenantOperations;
    private RoomOperations roomOperations;
    private BillOperations billOperations;
    private MessageOperations messageOperations;
    private AdminOperations adminOperations;
    private TableView<Bill> billTable;
    private TableView<Message> messageTable;
    private TableView<Admin> adminTable;
    private static ObservableList<Message> messageList;
    private static ObservableList<Admin> adminList;
    private String tenantName;
    
    // Tambahkan tombol Send Message sebagai field kelas agar kita bisa mengontrol visibilitasnya
    private Button sendMessageButton;

    // Konstruktor yang menerima objek Stage dan nama penghuni
    public TenantDashboard(Stage primaryStage, String tenantName) {
        this.primaryStage = primaryStage;
        this.tenantName = tenantName;
        try {
            tenantOperations = new TenantOperations();
            roomOperations = new RoomOperations();
            billOperations = new BillOperations();
            messageOperations = new MessageOperations();
            adminOperations = new AdminOperations();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metode yang mengembalikan objek VBox yang berisi elemen-elemen antarmuka pengguna dashboard penghuni
    public VBox getView() {
        VBox vbox = new VBox(20); // Menambahkan jarak lebih antara konten
        vbox.setPadding(new Insets(20)); // Menambahkan padding di sekitar VBox
        vbox.setStyle("-fx-background-color: #1b9ae3; -fx-font-size: 18px;");

        // Membuat BorderPane untuk mengatur layout
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10)); // Menambahkan padding di sekitar BorderPane

        // Sidebar menggunakan ListView dengan "Tenant Dashboard" termasuk
        ListView<String> sidebar = new ListView<>();
        sidebar.setItems(FXCollections.observableArrayList("Tagihan", "Pesan", "Informasi Admin"));
        sidebar.setPrefWidth(200);  // Mengatur lebar sidebar
        sidebar.setStyle("-fx-font-size: 18px;"); // Ukuran font sidebar

        // Mengatur lebar yang sama untuk semua item dalam sidebar
        sidebar.setCellFactory(param -> {
            ListCell<String> cell = new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item);
                    }
                }
            };
            cell.setPrefWidth(200);  // Menyamakan lebar item dalam ListView
            return cell;
        });

        // Membuat VBox untuk setiap tab konten
        VBox billVBox = createBillTab();
        VBox messageVBox = createMessageTab();
        VBox adminVBox = createAdminTab();

        // Mengatur konten default menjadi Bill Tab
        borderPane.setCenter(billVBox);
        BorderPane.setMargin(billVBox, new Insets(10)); // Menambahkan margin di sekitar tabel Bills

        // Membuat tombol "Send Message" tetapi tidak menambahkannya ke layout
        sendMessageButton = new Button("Kirim Pesan");
        sendMessageButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px 10px;");
        sendMessageButton.setOnAction(e -> sendMessage());
        sendMessageButton.setPadding(new Insets(5));

        // Menyembunyikan tombol "Send Message" secara awal
        sendMessageButton.setVisible(false);

        VBox sendMessageVBox = new VBox(10);
        sendMessageVBox.setPadding(new Insets(10));
        sendMessageVBox.getChildren().add(sendMessageButton); // Menambahkan tombol ke VBox

        // Membuat tombol "Logout" dan menempatkannya di VBox di bawah tombol sendMessage
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px 10px;");
        logoutButton.setOnAction(e -> logout());
        logoutButton.setPadding(new Insets(5));

        VBox logoutVBox = new VBox(20);
        logoutVBox.setPadding(new Insets(10));
        logoutVBox.getChildren().add(logoutButton);

        // Menempatkan tombol di bagian bawah BorderPane
        VBox sidebarAndButtons = new VBox(10, sidebar, sendMessageVBox, logoutVBox);
        sidebarAndButtons.setStyle("-fx-background-color: white; -fx-padding: 10px;");
        borderPane.setLeft(sidebarAndButtons);

        // Menambahkan teks "Tenant Dashboard" di atas sidebar
        Label tenantDashboardLabel = new Label("Dashboard Penghuni");
        tenantDashboardLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        VBox sidebarContainer = new VBox(10, tenantDashboardLabel, sidebarAndButtons);
        sidebarContainer.setStyle("-fx-background-color: #1b9ae3; -fx-padding: 10px;");
        borderPane.setLeft(sidebarContainer);

        vbox.getChildren().add(borderPane);

        // Listener sidebar untuk mengubah tampilan berdasarkan pilihan
        sidebar.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Tagihan":
                    borderPane.setCenter(billVBox);
                    BorderPane.setMargin(billVBox, new Insets(10)); // Menambahkan margin di sekitar tabel Bills
                    hideSendMessageButton(); // Menyembunyikan tombol untuk view Bills
                    break;
                case "Pesan":
                    borderPane.setCenter(messageVBox);
                    BorderPane.setMargin(messageVBox, new Insets(10)); // Menambahkan margin di sekitar tabel Messages
                    showSendMessageButton(); // Menampilkan tombol untuk view Messages
                    break;
                case "Informasi Admin":
                    borderPane.setCenter(adminVBox);
                    BorderPane.setMargin(adminVBox, new Insets(10)); // Menambahkan margin di sekitar tabel Admin Info
                    hideSendMessageButton(); // Menyembunyikan tombol untuk view Admin Info
                    break;
            }
        });

        return vbox;
    }

    // Metode untuk membuat tab Tagihan
    private VBox createBillTab() {
        VBox billVBox = new VBox(10);
        billTable = new TableView<>();
        setupBillTableView();
        billTable.setPrefHeight(800); // Mengatur tinggi tabel agar sama dengan sidebar
        billTable.setStyle("-fx-background-color: white; -fx-padding: 10px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 15, 0.0, 0, 10);");
        billVBox.getChildren().addAll(billTable);
        return billVBox;
    }

    // Metode untuk membuat tab Pesan
    private VBox createMessageTab() {
        VBox messageVBox = new VBox(10);
        messageTable = new TableView<>();
        setupMessageTableView();

        // Mengatur tinggi untuk TableView dan VBox agar konsisten
        messageTable.setPrefHeight(800);  // Mengatur tinggi tetap untuk table message
        messageTable.setStyle("-fx-background-color: white; -fx-padding: 10px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 15, 0.0, 0, 10);");
        messageVBox.setPrefHeight(800);   // Mengatur tinggi VBox agar sama dengan sidebar

        HBox buttonHBox = new HBox(10);
        buttonHBox.setPadding(new Insets(0, 0, 0, 80));

        messageVBox.getChildren().addAll(messageTable, buttonHBox);
        return messageVBox;
    }

    // Metode untuk membuat tab Informasi Admin
    private VBox createAdminTab() {
        VBox adminVBox = new VBox(10);
        adminTable = new TableView<>();
        setupAdminTableView();
        adminTable.setPrefHeight(800); // Mengatur tinggi tabel agar sama dengan sidebar
        adminTable.setStyle("-fx-background-color: white; -fx-padding: 10px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 15, 0.0, 0, 10);");
        adminVBox.getChildren().addAll(adminTable);
        return adminVBox;
    }

    // Metode untuk mengatur kolom-kolom tabel Tagihan
    private void setupBillTableView() {
        TableColumn<Bill, Integer> idTransactionColumn = new TableColumn<>("ID Transaksi");
        idTransactionColumn.setCellValueFactory(cellData -> cellData.getValue().idTransactionProperty().asObject());
        idTransactionColumn.setStyle("-fx-font-size: 12px;");

        TableColumn<Bill, Integer> idTenantColumn = new TableColumn<>("ID Penghuni");
        idTenantColumn.setCellValueFactory(cellData -> cellData.getValue().idTenantProperty().asObject());
        idTenantColumn.setStyle("-fx-font-size: 12px;");

        TableColumn<Bill, String> nameColumn = new TableColumn<>("Nama");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameColumn.setStyle("-fx-font-size: 12px;");

        TableColumn<Bill, Integer> idRoomColumn = new TableColumn<>("ID Kamar");
        idRoomColumn.setCellValueFactory(cellData -> cellData.getValue().idRoomProperty().asObject());
        idRoomColumn.setStyle("-fx-font-size: 12px;");

        TableColumn<Bill, Double> roomPriceColumn = new TableColumn<>("Harga Kamar");
        roomPriceColumn.setCellValueFactory(cellData -> cellData.getValue().roomPriceProperty().asObject());
        roomPriceColumn.setStyle("-fx-font-size: 12px;");

        TableColumn<Bill, java.sql.Date> paymentDateColumn = new TableColumn<>("Tanggal Bayar");
        paymentDateColumn.setCellValueFactory(cellData -> cellData.getValue().paymentDateProperty());
        paymentDateColumn.setStyle("-fx-font-size: 12px;");

        TableColumn<Bill, Double> amountPaidColumn = new TableColumn<>("Harga Bayar");
        amountPaidColumn.setCellValueFactory(cellData -> cellData.getValue().amountPaidProperty().asObject());
        amountPaidColumn.setStyle("-fx-font-size: 12px;");

        billTable.getColumns().addAll(idTransactionColumn, idTenantColumn, nameColumn, idRoomColumn, roomPriceColumn, paymentDateColumn, amountPaidColumn);

        try {
            List<Bill> bills = billOperations.getAllBills();
            ObservableList<Bill> billList = FXCollections.observableArrayList(bills);
            billTable.setItems(billList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metode untuk mengatur kolom-kolom tabel Pesan
    private void setupMessageTableView() {
        TableColumn<Message, Integer> idMessageColumn = new TableColumn<>("ID Pesan");
        idMessageColumn.setCellValueFactory(cellData -> cellData.getValue().idMessageProperty().asObject());
        idMessageColumn.setStyle("-fx-font-size: 12px;");

        TableColumn<Message, Integer> idTenantColumn = new TableColumn<>("ID Penghuni");
        idTenantColumn.setCellValueFactory(cellData -> cellData.getValue().idTenantProperty().asObject());
        idTenantColumn.setStyle("-fx-font-size: 12px;");

        TableColumn<Message, String> nameColumn = new TableColumn<>("Nama");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameColumn.setStyle("-fx-font-size: 12px;");

        TableColumn<Message, String> messageColumn = new TableColumn<>("Pesan");
        messageColumn.setCellValueFactory(cellData -> cellData.getValue().messageProperty());
        messageColumn.setStyle("-fx-font-size: 12px;");

        messageTable.getColumns().addAll(idMessageColumn, idTenantColumn, nameColumn, messageColumn);

        try {
            List<Message> messages = messageOperations.getAllMessages();
            messageList = FXCollections.observableArrayList(messages);
            messageTable.setItems(messageList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metode untuk mengatur kolom-kolom tabel Informasi Admin
    private void setupAdminTableView() {
        TableColumn<Admin, Integer> idAdminColumn = new TableColumn<>("ID Admin");
        idAdminColumn.setCellValueFactory(cellData -> cellData.getValue().idUserProperty().asObject());
        idAdminColumn.setStyle("-fx-font-size: 12px;");

        TableColumn<Admin, String> kostaddressColumn = new TableColumn<>("Alamat Kost");
        kostaddressColumn.setCellValueFactory(cellData -> cellData.getValue().kostAddressProperty());
        kostaddressColumn.setStyle("-fx-font-size: 12px;");

        TableColumn<Admin, String> accountnameColumn = new TableColumn<>("Nama Akun");
        accountnameColumn.setCellValueFactory(cellData -> cellData.getValue().accountNameProperty());
        accountnameColumn.setStyle("-fx-font-size: 12px;");

        TableColumn<Admin, String> accountnumberColumn = new TableColumn<>("Nomor Akun");
        accountnumberColumn.setCellValueFactory(cellData -> cellData.getValue().accountNumberProperty());
        accountnumberColumn.setStyle("-fx-font-size: 12px;");

        adminTable.getColumns().addAll(idAdminColumn, kostaddressColumn, accountnameColumn, accountnumberColumn);

        try {
            List<Admin> admins = adminOperations.getAllAdmins();
            adminList = FXCollections.observableArrayList(admins);
            adminTable.setItems(adminList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metode untuk menampilkan dialog pengiriman pesan
    private void sendMessage() {
        Dialog<Message> dialog = new Dialog<>();
        dialog.setTitle("Kirim Pesan");
        dialog.setHeaderText("Masukkan detail pesan:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // TextField dengan styling warna biru
        TextField messageField = new TextField();
        messageField.setPromptText("Tulis pesan Anda...");
        
        // Styling TextField dengan warna biru
        messageField.setStyle(
            "-fx-background-color: #e0f7fa; " +   // Warna latar belakang biru muda
            "-fx-border-color: #00796b; " +       // Warna border biru kehijauan
            "-fx-border-width: 2px; " + 
            "-fx-padding: 10px; " + 
            "-fx-font-size: 14px; " + 
            "-fx-border-radius: 5px; " + 
            "-fx-focus-color: #00796b;"  // Warna border saat fokus
        );

        Label messageLabel = new Label("Pesan:");

        // HBox dengan styling
        HBox messageBox = new HBox(10);
        messageBox.getChildren().addAll(messageLabel, messageField);
        messageBox.setPadding(new Insets(0, 0, 0, 20));
        messageBox.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 10px; -fx-padding: 10px; -fx-effect: innershadow(gaussian, rgba(0, 0, 0, 0.2), 5, 0.0, 0, 0);");

        grid.add(messageBox, 1, 0, 2, 1);

        dialog.getDialogPane().setContent(grid);

        ButtonType sendButton = new ButtonType("Kirim", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(sendButton, cancelButton);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == sendButton) {
                return new Message(0, 1, tenantName, messageField.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(message -> {
            try {
                messageOperations.addMessage(message);
                messageList.add(message);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // Metode untuk logout dan kembali ke halaman login
    private void logout() {
        LoginView loginView = new LoginView(primaryStage);
        Scene scene = new Scene(loginView.getView(), 400, 300);
        primaryStage.setTitle("Kost Management App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Metode untuk menampilkan tombol "Send Message"
    private void showSendMessageButton() {
        sendMessageButton.setVisible(true);
    }

    // Metode untuk menyembunyikan tombol "Send Message"
    private void hideSendMessageButton() {
        sendMessageButton.setVisible(false);
    }
}
