package view;

import Operations.BillOperations;
import Operations.MessageOperations;
import Operations.RoomOperations;
import Operations.TenantOperations;
import Operations.AdminOperations;
import Operations.LoginOperations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import model.Bill;
import model.Message;
import model.Room;
import model.Tenant;
import model.Admin;
import model.Login;

public class AdminDashboard {
    // Deklarasi variabel untuk stage utama
    private Stage primaryStage;
    
    // Deklarasi objek operasi untuk berbagai entitas
    private TenantOperations tenantOperations;
    private RoomOperations roomOperations;
    private BillOperations billOperations;
    private MessageOperations messageOperations;
    private AdminOperations adminOperations;
    private LoginOperations loginOperations;
    
    // Deklarasi tabel-tabel untuk menampilkan data
    private TableView<Tenant> tenantTable;
    private TableView<Room> roomTable;
    private TableView<Bill> billTable;
    private TableView<Message> messageTable;
    private TableView<Admin> adminTable;
    private TableView<Login> loginTable;
    
    // Deklarasi list-observable untuk data pesan, informasi admin, dan login
    private static ObservableList<Message> messageList;
    private static ObservableList<Admin> adminList;
    private static ObservableList<Login> loginList;

    // Konstruktor AdminDashboard yang menerima parameter Stage
    public AdminDashboard(Stage primaryStage) { 
        this.primaryStage = primaryStage; // Mengatur stage utama
        try {
            // Inisialisasi objek operasi untuk berbagai entitas
            tenantOperations = new TenantOperations();
            roomOperations = new RoomOperations();
            billOperations = new BillOperations();
            messageOperations = new MessageOperations();
            adminOperations = new AdminOperations();
            loginOperations = new LoginOperations();
        } catch (SQLException e) {
            e.printStackTrace(); // Menampilkan stack trace jika terjadi SQLException
        }
    }

    public BorderPane getView() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));
        borderPane.setStyle("-fx-background-color: white;"); 

        Label titleLabel = new Label("Admin Dashboard");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        ListView<String> sidebar = new ListView<>();
        sidebar.getItems().addAll("Kamar", "Penghuni", "Tagihan", "Pesan", "Informasi Admin", "Kelola Akun");
        sidebar.setStyle("-fx-background-color: #959c97; -fx-font-size: 16px;");
        sidebar.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Kamar":
                    borderPane.setCenter(getRoomView());
                    break;
                case "Penghuni":
                    borderPane.setCenter(getTenantView());
                    break;
                case "Tagihan":
                    borderPane.setCenter(getBillView());
                    break;
                case "Pesan":
                    borderPane.setCenter(getMessageView());
                    break;
                case "Informasi Admin":
                    borderPane.setCenter(getAdminView());
                    break;
                case "Kelola Akun":
                    borderPane.setCenter(getLoginView());
                    break;
            }
        });

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white; -fx-font-size: 14px;");
        logoutButton.setOnAction(e -> logout());

        VBox sidebarVBox = new VBox(10);
        sidebarVBox.getChildren().addAll(titleLabel, sidebar, logoutButton);
        sidebarVBox.setStyle("-fx-background-color: white; -fx-padding: 10px;");

        borderPane.setLeft(sidebarVBox);
        borderPane.setCenter(getTenantView()); // Default view

        return borderPane;
    }

    private VBox getRoomView() {
        VBox vbox = new VBox(10);
        roomTable = new TableView<>();
        setupRoomTableView();
        roomTable.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");
        roomTable.setEffect(new DropShadow(10, 0, 0, javafx.scene.paint.Color.GRAY));

        Button addRoomButton = new Button("Tambah");
        Button updateRoomButton = new Button("Edit");
        Button deleteRoomButton = new Button("Hapus");
        addRoomButton.setOnAction(e -> addRoom());
        updateRoomButton.setOnAction(e -> updateRoom());
        deleteRoomButton.setOnAction(e -> deleteRoom());

        addRoomButton.setStyle("-fx-background-color: #23c40e; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");
        updateRoomButton.setStyle("-fx-background-color: #0602d4; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");
        deleteRoomButton.setStyle("-fx-background-color: #e00b0b; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");

        VBox roomVBox = new VBox(10);
        roomVBox.getChildren().addAll(roomTable, addRoomButton, updateRoomButton, deleteRoomButton);
        roomVBox.setStyle("-fx-background-color: #1b9ae3; -fx-padding: 10px;");
        roomVBox.setEffect(new DropShadow(10, 0, 0, javafx.scene.paint.Color.GRAY));

        return roomVBox;
    }

    private VBox getTenantView() {
        VBox vbox = new VBox(10);
        tenantTable = new TableView<>();
        setupTenantTableView();
        tenantTable.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");
        tenantTable.setEffect(new DropShadow(10, 0, 0, javafx.scene.paint.Color.GRAY));

        Button addTenantButton = new Button("Tambah");
        Button updateTenantButton = new Button("Edit");
        Button deleteTenantButton = new Button("Hapus");
        addTenantButton.setOnAction(e -> addTenant());
        updateTenantButton.setOnAction(e -> updateTenant());
        deleteTenantButton.setOnAction(e -> deleteTenant());

        addTenantButton.setStyle("-fx-background-color: #23c40e; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");
        updateTenantButton.setStyle("-fx-background-color: #0602d4; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");
        deleteTenantButton.setStyle("-fx-background-color: #e00b0b; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");

        VBox tenantVBox = new VBox(10);
        tenantVBox.getChildren().addAll(tenantTable, addTenantButton, updateTenantButton, deleteTenantButton);
        tenantVBox.setStyle("-fx-background-color: #1b9ae3; -fx-padding: 10px;");
        tenantVBox.setEffect(new DropShadow(10, 0, 0, javafx.scene.paint.Color.GRAY));

        return tenantVBox;
    }

    private VBox getBillView() {
        VBox vbox = new VBox(10);
        billTable = new TableView<>();
        setupBillTableView();
        billTable.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");
        billTable.setEffect(new DropShadow(10, 0, 0, javafx.scene.paint.Color.GRAY));

        Button addBillButton = new Button("Tambah");
        Button updateBillButton = new Button("Edit");
        Button deleteBillButton = new Button("Hapus");
        addBillButton.setOnAction(e -> addBill());
        updateBillButton.setOnAction(e -> updateBill());
        deleteBillButton.setOnAction(e -> deleteBill());

        addBillButton.setStyle("-fx-background-color: #23c40e; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");
        updateBillButton.setStyle("-fx-background-color: #0602d4; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");
        deleteBillButton.setStyle("-fx-background-color: #e00b0b; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");

        VBox billVBox = new VBox(10);
        billVBox.getChildren().addAll(billTable, addBillButton, updateBillButton, deleteBillButton);
        billVBox.setStyle("-fx-background-color: #1b9ae3; -fx-padding: 10px;");
        billVBox.setEffect(new DropShadow(10, 0, 0, javafx.scene.paint.Color.GRAY));

        return billVBox;
    }

    private VBox getMessageView() {
        VBox vbox = new VBox(10);
        messageTable = new TableView<>();
        setupMessageTableView();
        messageTable.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");
        messageTable.setEffect(new DropShadow(10, 0, 0, javafx.scene.paint.Color.GRAY));

        Button deleteMessageButton = new Button("Hapus Pesan");
        deleteMessageButton.setOnAction(e -> deleteMessage());

        deleteMessageButton.setStyle("-fx-background-color: #e00b0b; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");

        VBox messageVBox = new VBox(10);
        messageVBox.getChildren().addAll(messageTable, deleteMessageButton);
        messageVBox.setStyle("-fx-background-color: #1b9ae3; -fx-padding: 10px;");
        messageVBox.setEffect(new DropShadow(10, 0, 0, javafx.scene.paint.Color.GRAY));

        return messageVBox;
    }

    private VBox getAdminView() {
        VBox vbox = new VBox(10);
        adminTable = new TableView<>();
        setupAdminTableView();
        adminTable.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");
        adminTable.setEffect(new DropShadow(10, 0, 0, javafx.scene.paint.Color.GRAY));

        Button addAdminButton = new Button("Tambah");
        Button updateAdminButton = new Button("Edit");
        Button deleteAdminButton = new Button("Hapus");
        addAdminButton.setOnAction(e -> addAdmin());
        updateAdminButton.setOnAction(e -> updateAdmin());
        deleteAdminButton.setOnAction(e -> deleteAdmin());

        addAdminButton.setStyle("-fx-background-color: #23c40e; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");
        updateAdminButton.setStyle("-fx-background-color: #0602d4; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");
        deleteAdminButton.setStyle("-fx-background-color: #e00b0b; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");

        VBox adminVBox = new VBox(10);
        adminVBox.getChildren().addAll(adminTable, addAdminButton, updateAdminButton, deleteAdminButton);
        adminVBox.setStyle("-fx-background-color: #1b9ae3; -fx-padding: 10px;");
        adminVBox.setEffect(new DropShadow(10, 0, 0, javafx.scene.paint.Color.GRAY));

        return adminVBox;
    }

    private VBox getLoginView() {
        VBox vbox = new VBox(10);
        loginTable = new TableView<>();
        setupLoginTableView();
        loginTable.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");
        loginTable.setEffect(new DropShadow(10, 0, 0, javafx.scene.paint.Color.GRAY));

        Button addLoginButton = new Button("Tambah");
        Button updateLoginButton = new Button("Edit");
        Button deleteLoginButton = new Button("Hapus");
        addLoginButton.setOnAction(e -> addLogin());
        updateLoginButton.setOnAction(e -> updateLogin());
        deleteLoginButton.setOnAction(e -> deleteLogin());

        addLoginButton.setStyle("-fx-background-color: #23c40e; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");
        updateLoginButton.setStyle("-fx-background-color: #0602d4; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");
        deleteLoginButton.setStyle("-fx-background-color: #e00b0b; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");

        VBox loginVBox = new VBox(10);
        loginVBox.getChildren().addAll(loginTable, addLoginButton, updateLoginButton, deleteLoginButton);
        loginVBox.setStyle("-fx-background-color: #1b9ae3; -fx-padding: 10px;");
        loginVBox.setEffect(new DropShadow(10, 0, 0, javafx.scene.paint.Color.GRAY));

        return loginVBox;
    }

    private void setupTenantTableView() {
        // Membuat kolom ID Penghuni
        TableColumn<Tenant, Integer> idTenantColumn = new TableColumn<>("ID Penghuni");
        // Mengatur nilai sel kolom berdasarkan properti idTenant dari objek Tenant
        idTenantColumn.setCellValueFactory(cellData -> cellData.getValue().idTenantProperty().asObject());

        // Membuat kolom Nama
        TableColumn<Tenant, String> nameColumn = new TableColumn<>("Nama");
        // Mengatur nilai sel kolom berdasarkan properti name dari objek Tenant
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        // Membuat kolom Nomor HP
        TableColumn<Tenant, String> phoneNumberColumn = new TableColumn<>("Nomor HP");
        // Mengatur nilai sel kolom berdasarkan properti phoneNumber dari objek Tenant
        phoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());

        // Membuat kolom Alamat
        TableColumn<Tenant, String> addressColumn = new TableColumn<>("Alamat");
        // Mengatur nilai sel kolom berdasarkan properti address dari objek Tenant
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());

        // Membuat kolom ID Kamar
        TableColumn<Tenant, Integer> idRoomColumn = new TableColumn<>("ID Kamar");
        // Mengatur nilai sel kolom berdasarkan properti idRoom dari objek Tenant
        idRoomColumn.setCellValueFactory(cellData -> cellData.getValue().idRoomProperty().asObject());

        // Membuat kolom Harga Kamar
        TableColumn<Tenant, Double> roomPriceColumn = new TableColumn<>("Harga Kamar");
        // Mengatur nilai sel kolom berdasarkan properti roomPrice dari objek Tenant
        roomPriceColumn.setCellValueFactory(cellData -> cellData.getValue().roomPriceProperty().asObject());

        // Membuat kolom Tanggal Masuk
        TableColumn<Tenant, Date> checkInDateColumn = new TableColumn<>("Tanggal Masuk");
        // Mengatur nilai sel kolom berdasarkan properti checkInDate dari objek Tenant
        checkInDateColumn.setCellValueFactory(cellData -> cellData.getValue().checkInDateProperty());

        // Membuat kolom Tanggal Bayar
        TableColumn<Tenant, Date> checkOutDateColumn = new TableColumn<>("Tanggal Bayar");
        // Mengatur nilai sel kolom berdasarkan properti checkOutDate dari objek Tenant
        checkOutDateColumn.setCellValueFactory(cellData -> cellData.getValue().payDateProperty());

        // Menambahkan semua kolom ke dalam TableView tenantTable
        tenantTable.getColumns().addAll(idTenantColumn, nameColumn, phoneNumberColumn, addressColumn, idRoomColumn, roomPriceColumn, checkInDateColumn, checkOutDateColumn);

        try {
            // Mengambil semua data Tenant dari database menggunakan tenantOperations
            List<Tenant> tenants = tenantOperations.getAllTenants();
            // Mengonversi List Tenant menjadi ObservableList
            ObservableList<Tenant> tenantList = FXCollections.observableArrayList(tenants);
            // Mengatur items dari tenantTable dengan ObservableList Tenant
            tenantTable.setItems(tenantList);
        } catch (SQLException e) {
            // Menampilkan stack trace jika terjadi SQLException
            e.printStackTrace();
        }
    }

    private void setupRoomTableView() {
        TableColumn<Room, Integer> idRoomColumn = new TableColumn<>("ID Kamar");
        idRoomColumn.setCellValueFactory(cellData -> cellData.getValue().idRoomProperty().asObject());

        TableColumn<Room, String> facilitiesColumn = new TableColumn<>("Fasilitas");
        facilitiesColumn.setCellValueFactory(cellData -> cellData.getValue().facilitiesProperty());

        TableColumn<Room, String> sizeColumn = new TableColumn<>("Ukuran");
        sizeColumn.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());

        TableColumn<Room, Double> roomPriceColumn = new TableColumn<>("Harga Kamar");
        roomPriceColumn.setCellValueFactory(cellData -> cellData.getValue().roomPriceProperty().asObject());

        TableColumn<Room, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        roomTable.getColumns().addAll(idRoomColumn, facilitiesColumn, sizeColumn, roomPriceColumn, statusColumn);

        try {
            List<Room> rooms = roomOperations.getAllRooms();
            ObservableList<Room> roomList = FXCollections.observableArrayList(rooms);
            roomTable.setItems(roomList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupBillTableView() {
        TableColumn<Bill, Integer> idTransactionColumn = new TableColumn<>("ID Transaksi");
        idTransactionColumn.setCellValueFactory(cellData -> cellData.getValue().idTransactionProperty().asObject());

        TableColumn<Bill, Integer> idTenantColumn = new TableColumn<>("ID Penghuni");
        idTenantColumn.setCellValueFactory(cellData -> cellData.getValue().idTenantProperty().asObject());

        TableColumn<Bill, String> nameColumn = new TableColumn<>("Nama");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Bill, Integer> idRoomColumn = new TableColumn<>("ID Kamar");
        idRoomColumn.setCellValueFactory(cellData -> cellData.getValue().idRoomProperty().asObject());

        TableColumn<Bill, Double> roomPriceColumn = new TableColumn<>("Harga Kamar");
        roomPriceColumn.setCellValueFactory(cellData -> cellData.getValue().roomPriceProperty().asObject());

        TableColumn<Bill, Date> paymentDateColumn = new TableColumn<>("Tanggal Bayar");
        paymentDateColumn.setCellValueFactory(cellData -> cellData.getValue().paymentDateProperty());

        TableColumn<Bill, Double> amountPaidColumn = new TableColumn<>("Harga Bayar");
        amountPaidColumn.setCellValueFactory(cellData -> cellData.getValue().amountPaidProperty().asObject());

        billTable.getColumns().addAll(idTransactionColumn, idTenantColumn, nameColumn, idRoomColumn, roomPriceColumn, paymentDateColumn, amountPaidColumn);

        try {
            List<Bill> bills = billOperations.getAllBills();
            ObservableList<Bill> billList = FXCollections.observableArrayList(bills);
            billTable.setItems(billList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupMessageTableView() {
        TableColumn<Message, Integer> idMessageColumn = new TableColumn<>("ID Pesan");
        idMessageColumn.setCellValueFactory(cellData -> cellData.getValue().idMessageProperty().asObject());

        TableColumn<Message, Integer> idTenantColumn = new TableColumn<>("ID Penghuni");
        idTenantColumn.setCellValueFactory(cellData -> cellData.getValue().idTenantProperty().asObject());

        TableColumn<Message, String> nameColumn = new TableColumn<>("Nama");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Message, String> messageColumn = new TableColumn<>("Pesan");
        messageColumn.setCellValueFactory(cellData -> cellData.getValue().messageProperty());

        messageTable.getColumns().addAll(idMessageColumn, idTenantColumn, nameColumn, messageColumn);

        try {
            List<Message> messages = messageOperations.getAllMessages();
            messageList = FXCollections.observableArrayList(messages);
            messageTable.setItems(messageList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupAdminTableView() {
        TableColumn<Admin, Integer> idAdminColumn = new TableColumn<>("ID Admin");
        idAdminColumn.setCellValueFactory(cellData -> cellData.getValue().idAdminProperty().asObject());

        TableColumn<Admin, String> kostAddressColumn = new TableColumn<>("Alamat Kost");
        kostAddressColumn.setCellValueFactory(cellData -> cellData.getValue().kostAddressProperty());

        TableColumn<Admin, String> accountNameColumn = new TableColumn<>("Nama Akun");
        accountNameColumn.setCellValueFactory(cellData -> cellData.getValue().accountNameProperty());

        TableColumn<Admin, String> accountNumberColumn = new TableColumn<>("Nomor Akun");
        accountNumberColumn.setCellValueFactory(cellData -> cellData.getValue().accountNumberProperty());

        adminTable.getColumns().addAll(idAdminColumn, kostAddressColumn, accountNameColumn, accountNumberColumn);

        try {
            List<Admin> admins = adminOperations.getAllAdmins();
            adminList = FXCollections.observableArrayList(admins);
            adminTable.setItems(adminList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupLoginTableView() {
        TableColumn<Login, Integer> idAccountColumn = new TableColumn<>("ID Akun");
        idAccountColumn.setCellValueFactory(cellData -> cellData.getValue().idAccountProperty().asObject());

        TableColumn<Login, String> nameColumn = new TableColumn<>("Nama");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        TableColumn<Login, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());

        TableColumn<Login, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());

        TableColumn<Login, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());

        loginTable.getColumns().addAll(idAccountColumn, nameColumn, usernameColumn, passwordColumn, roleColumn);

        try {
            List<Login> logins = loginOperations.getAllLogins();
            loginList = FXCollections.observableArrayList(logins);
            loginTable.setItems(loginList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean validateInput(TextField nameField, TextField phoneNumberField, TextField addressField, ComboBox<Integer> roomComboBox, TextField roomPriceField, DatePicker checkInDatePicker, DatePicker PayDatePicker) {
        if (nameField.getText().isEmpty()) {
            showAlert("Kesalahan", "Name Harus Diisi.");
            return false;
        }
        if (phoneNumberField.getText().isEmpty()) {
            showAlert("Kesalahan", "Nomor HP Harus Diisi.");
            return false;
        }
        if (addressField.getText().isEmpty()) {
            showAlert("Kesalahan", "Alamat Harus Diisi.");
            return false;
        }
        if (roomComboBox.getValue() == null) {
            showAlert("Kesalahan", "ID Kamar Harus Diisi.");
            return false;
        }
        if (roomPriceField.getText().isEmpty()) {
            showAlert("Kesalahan", "Harga Kamar Harus Diisi.");
            return false;
        }
        if (checkInDatePicker.getValue() == null) {
            showAlert("Kesalahan", "Tanggal Masuk Harus Diisi.");
            return false;
        }
        if (PayDatePicker.getValue() == null) {
            showAlert("Kesalahan", "Tanggal Bayar Harus Diisi.");
            return false;
        }
        return true;
    }

    private void addTenant() {
        Dialog<Tenant> dialog = new Dialog<>();
        dialog.setTitle("Tambah Penghuni");
        dialog.setHeaderText("Masukan Detail Penghuni:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");

        TextField nameField = new TextField();
        nameField.setPromptText("Nama");
        grid.add(new Label("Nama:"), 0, 0);
        grid.add(nameField, 1, 0);

        TextField phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Nomor HP");
        grid.add(new Label("Nomor HP:"), 0, 1);
        grid.add(phoneNumberField, 1, 1);

        TextField addressField = new TextField();
        addressField.setPromptText("Alamat");
        grid.add(new Label("Alamat:"), 0, 2);
        grid.add(addressField, 1, 2);

        ComboBox<Integer> roomComboBox = new ComboBox<>();
        try {
            RoomOperations roomOperations = new RoomOperations();
            List<Room> rooms = roomOperations.getAllRooms();
            for (Room room : rooms) {
                if ("kosong".equalsIgnoreCase(room.getStatus())) {
                    roomComboBox.getItems().add(room.getIdRoom());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Kesalahan", "Gagal memuat kamar.");
            return;
        }
        grid.add(new Label("ID Kamar:"), 0, 3);
        grid.add(roomComboBox, 1, 3);

        TextField roomPriceField = new TextField();
        roomPriceField.setPromptText("Harga Kamar");
        roomPriceField.setEditable(false); // Make the field non-editable
        grid.add(new Label("Harga Kamar:"), 0, 4);
        grid.add(roomPriceField, 1, 4);

        DatePicker checkInDatePicker = new DatePicker();
        grid.add(new Label("Tanggal Masuk:"), 0, 5);
        grid.add(checkInDatePicker, 1, 5);

        DatePicker PayDatePicker = new DatePicker();
        grid.add(new Label("Tanggal Bayar:"), 0, 6);
        grid.add(PayDatePicker, 1, 6);

        // Add event listener to roomComboBox
        roomComboBox.setOnAction(event -> {
            Integer selectedRoomId = roomComboBox.getValue();
            if (selectedRoomId != null) {
                try {
                    RoomOperations roomOperations = new RoomOperations();
                    Room selectedRoom = roomOperations.getAllRooms().stream()
                                                      .filter(room -> room.getIdRoom() == selectedRoomId)
                                                      .findFirst()
                                                      .orElse(null);
                    if (selectedRoom != null) {
                        roomPriceField.setText(String.valueOf(selectedRoom.getRoomPrice()));
                    } else {
                        roomPriceField.setText("");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Kesalahan", "Gagal memuat harga kamar.");
                }
            } else {
                roomPriceField.setText("");
            }
        });

        dialog.getDialogPane().setContent(grid);

        ButtonType addButton = new ButtonType("Tambah", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                if (validateInput(nameField, phoneNumberField, addressField, roomComboBox, roomPriceField, checkInDatePicker, PayDatePicker)) {
                    try {
                        int idTenant = tenantTable.getItems().size() + 1; // Assuming ID is auto-incremented
                        String name = nameField.getText();
                        String phoneNumber = phoneNumberField.getText();
                        String address = addressField.getText();
                        int idRoom = roomComboBox.getValue();
                        double roomPrice = Double.parseDouble(roomPriceField.getText());
                        java.sql.Date checkInDate = java.sql.Date.valueOf(checkInDatePicker.getValue());
                        java.sql.Date PayDate = java.sql.Date.valueOf(PayDatePicker.getValue());

                        Tenant tenant = new Tenant(idTenant, name, phoneNumber, address, idRoom, roomPrice, checkInDate, PayDate);
                        tenantOperations.addTenant(tenant);
                        tenantTable.getItems().add(tenant);

                        // Update room status to "isi"
                        RoomOperations roomOperations = new RoomOperations();
                        roomOperations.updateRoomStatus(idRoom, "isi");

                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert("Kesalahan", "Gagal tambah penghuni.");
                    } catch (NumberFormatException e) {
                        showAlert("Kesalahan", "Harga kamar tidak valid.");
                    }
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void updateTenant() {
    Tenant selectedTenant = tenantTable.getSelectionModel().getSelectedItem();
    if (selectedTenant != null) {
        Dialog<Tenant> dialog = new Dialog<>();
        dialog.setTitle("Edit Penghuni");
        dialog.setHeaderText("Edit Detail Penghuni:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");

        TextField nameField = new TextField(selectedTenant.getName());
        nameField.setPromptText("Nama");
        grid.add(new Label("Nama:"), 0, 0);
        grid.add(nameField, 1, 0);

        TextField phoneNumberField = new TextField(selectedTenant.getPhoneNumber());
        phoneNumberField.setPromptText("Nomor HP");
        grid.add(new Label("Nomor HP:"), 0, 1);
        grid.add(phoneNumberField, 1, 1);

        TextField addressField = new TextField(selectedTenant.getAddress());
        addressField.setPromptText("Alamat");
        grid.add(new Label("Alamat:"), 0, 2);
        grid.add(addressField, 1, 2);

        ComboBox<Integer> roomComboBox = new ComboBox<>();
        try {
            RoomOperations roomOperations = new RoomOperations();
            List<Room> rooms = roomOperations.getAllRooms();
            for (Room room : rooms) {
                if ("kosong".equalsIgnoreCase(room.getStatus())) {
                    roomComboBox.getItems().add(room.getIdRoom());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Kesalahan", "Gagal memuat kamar.");
            return;
        }
        roomComboBox.setValue(selectedTenant.getIdRoom());
        grid.add(new Label("ID Kamar:"), 0, 3);
        grid.add(roomComboBox, 1, 3);

        TextField roomPriceField = new TextField(String.valueOf(selectedTenant.getRoomPrice()));
        roomPriceField.setPromptText("Harga Kamar");
        roomPriceField.setEditable(false); // Make the field non-editable
        grid.add(new Label("Harga Kamar:"), 0, 4);
        grid.add(roomPriceField, 1, 4);

        DatePicker checkInDatePicker = new DatePicker(selectedTenant.getCheckInDate().toLocalDate());
        grid.add(new Label("Tanggal Masuk:"), 0, 5);
        grid.add(checkInDatePicker, 1, 5);

        DatePicker PayDatePicker = new DatePicker(selectedTenant.getPayDate().toLocalDate());
        grid.add(new Label("Tanggal Bayar:"), 0, 6);
        grid.add(PayDatePicker, 1, 6);

        // Add event listener to roomComboBox
        roomComboBox.setOnAction(event -> {
            Integer selectedRoomId = roomComboBox.getValue();
            if (selectedRoomId != null) {
                try {
                    RoomOperations roomOperations = new RoomOperations();
                    Room selectedRoom = roomOperations.getAllRooms().stream()
                                                      .filter(room -> room.getIdRoom() == selectedRoomId)
                                                      .findFirst()
                                                      .orElse(null);
                    if (selectedRoom != null) {
                        roomPriceField.setText(String.valueOf(selectedRoom.getRoomPrice()));
                    } else {
                        roomPriceField.setText("");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Kesalahan", "Gagal memuat kamar.");
                }
            } else {
                roomPriceField.setText("");
            }
        });

        dialog.getDialogPane().setContent(grid);

        ButtonType updateButton = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButton, cancelButton);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButton) {
                if (validateInput(nameField, phoneNumberField, addressField, roomComboBox, roomPriceField, checkInDatePicker, PayDatePicker)) {
                    try {
                        int idTenant = selectedTenant.getIdTenant();
                        String name = nameField.getText();
                        String phoneNumber = phoneNumberField.getText();
                        String address = addressField.getText();
                        int idRoom = roomComboBox.getValue();
                        double roomPrice = Double.parseDouble(roomPriceField.getText());
                        java.sql.Date checkInDate = java.sql.Date.valueOf(checkInDatePicker.getValue());
                        java.sql.Date PayDate = java.sql.Date.valueOf(PayDatePicker.getValue());

                        Tenant updatedTenant = new Tenant(idTenant, name, phoneNumber, address, idRoom, roomPrice, checkInDate, PayDate);
                        tenantOperations.updateTenant(updatedTenant);

                        // Perbarui item di ObservableList
                        ObservableList<Tenant> tenantList = tenantTable.getItems();
                        for (int i = 0; i < tenantList.size(); i++) {
                            if (tenantList.get(i).getIdTenant() == idTenant) {
                                tenantList.set(i, updatedTenant);
                                break;
                            }
                        }

                        // Update room status to "kosong" for the previous room
                        int previousRoomId = selectedTenant.getIdRoom();
                        if (previousRoomId != idRoom) {
                            RoomOperations roomOperations = new RoomOperations();
                            roomOperations.updateRoomStatus(previousRoomId, "kosong");
                        }

                        // Update room status to "isi" for the new room
                        roomOperations.updateRoomStatus(idRoom, "isi");

                        tenantTable.refresh();

                    } catch (SQLException e) {
                        e.printStackTrace();
                        showAlert("Kesalahan", "Gagal edit penghuni.");
                    } catch (NumberFormatException e) {
                        showAlert("Kesalahan", "Harga Kamar tidak sesuai.");
                    }
                }
            }
            return null;
        });

        dialog.showAndWait();
    } else {
        showAlert("Kesalahan", "Tidak ada penyewa yang dipilih.");
    }
}

    private void deleteTenant() {
        Tenant selectedTenant = tenantTable.getSelectionModel().getSelectedItem();
        if (selectedTenant != null) {
            try {
                tenantOperations.deleteTenant(selectedTenant.getIdTenant());
                tenantTable.getItems().remove(selectedTenant);

                // Update room status to "kosong"
                RoomOperations roomOperations = new RoomOperations();
                roomOperations.updateRoomStatus(selectedTenant.getIdRoom(), "kosong");

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Kesalahan", "Gagal menghapus penghuni.");
            }
        } else {
            showAlert("Kesalahan", "Pilih penghuni yang akan dihapus.");
        }
    }

    private void addRoom() {
        Dialog<Room> dialog = new Dialog<>();
        dialog.setTitle("Tambah Kamar");
        dialog.setHeaderText("Tambah Detail Kamar:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");

        Label facilitiesLabel = new Label("Fasilitas:");
        TextField facilitiesField = new TextField();
        facilitiesField.setPromptText("Masukan fasilitas");

        Label sizeLabel = new Label("Ukuran:");
        TextField sizeField = new TextField();
        sizeField.setPromptText("Masukan Ukuran");

        Label roomPriceLabel = new Label("Harga Kamar:");
        TextField roomPriceField = new TextField();
        roomPriceField.setPromptText("Masukan Harga Kamar");

        Label statusLabel = new Label("Status:");
        Label statusValue = new Label("Kosong"); // Default status
        statusValue.setStyle("-fx-font-weight: bold;");

        grid.add(facilitiesLabel, 0, 0);
        grid.add(facilitiesField, 1, 0);
        grid.add(sizeLabel, 0, 1);
        grid.add(sizeField, 1, 1);
        grid.add(roomPriceLabel, 0, 2);
        grid.add(roomPriceField, 1, 2);
        grid.add(statusLabel, 0, 3);
        grid.add(statusValue, 1, 3);

        ButtonType addButton = new ButtonType("Tambah", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                Room newRoom = new Room(
                    0, // ID will be set by the database
                    facilitiesField.getText(),
                    sizeField.getText(),
                    Double.parseDouble(roomPriceField.getText()),
                    "Kosong" // Default status
                );
                return newRoom;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(newRoom -> {
            try {
                roomOperations.addRoom(newRoom);
                refreshRoomTable(); // Ensure this method exists in your class
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void updateRoom() {
        Room selectedRoom = roomTable.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            Dialog<Room> dialog = new Dialog<>();
            dialog.setTitle("Edit Kamar");
            dialog.setHeaderText("Edit Detail Kamar:");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            grid.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");

            // Label and TextField for Facilities
            Label facilitiesLabel = new Label("Fasilitas:");
            TextField facilitiesField = new TextField(selectedRoom.getFacilities());
            grid.add(facilitiesLabel, 0, 0);
            grid.add(facilitiesField, 1, 0);

            // Label and TextField for Size
            Label sizeLabel = new Label("Ukuran:");
            TextField sizeField = new TextField(selectedRoom.getSize());
            grid.add(sizeLabel, 0, 1);
            grid.add(sizeField, 1, 1);

            // Label and TextField for Room Price
            Label roomPriceLabel = new Label("Harga Kamare:");
            TextField roomPriceField = new TextField(String.valueOf(selectedRoom.getRoomPrice()));
            grid.add(roomPriceLabel, 0, 2);
            grid.add(roomPriceField, 1, 2);

            // Label and Bold Text for Status
            Label statusLabel = new Label("Status:");
            Label statusValue = new Label(selectedRoom.getStatus());
            statusValue.setStyle("-fx-font-weight: bold;");
            grid.add(statusLabel, 0, 3);
            grid.add(statusValue, 1, 3);

            dialog.getDialogPane().setContent(grid);

            ButtonType buttonTypeOk = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, cancelButton);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == buttonTypeOk) {
                    Room updatedRoom = new Room(
                        selectedRoom.getIdRoom(),
                        facilitiesField.getText(),
                        sizeField.getText(),
                        Double.parseDouble(roomPriceField.getText()),
                        selectedRoom.getStatus() // Status tidak diubah
                    );
                    return updatedRoom;
                }
                return null;
            });

            dialog.showAndWait().ifPresent(room -> {
                try {
                    roomOperations.updateRoom(room);
                    refreshRoomTable(); // Pastikan metode ini ada di kelas AdminDashboard
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void deleteRoom() {
        Room selectedRoom = roomTable.getSelectionModel().getSelectedItem();
        if (selectedRoom != null) {
            // Check if the room status is "isi"
            if ("isi".equalsIgnoreCase(selectedRoom.getStatus())) {
                showAlert("Error", "Kamar tidak dapat dihapus karena statusnya adalah 'isi'.");
                return; // Exit the method if the room status is "isi"
            }

            try {
                roomOperations.deleteRoom(selectedRoom.getIdRoom());
                roomTable.getItems().remove(selectedRoom);
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Terjadi kesalahan saat menghapus kamar.");
            }
        } else {
            showAlert("Error", "Pilih Kamar yang akan dihapus.");
        }
    }

    private void refreshRoomTable() {
        try {
            RoomOperations roomOps = new RoomOperations();
            List<Room> rooms = roomOps.getAllRooms();
            ObservableList<Room> roomList = FXCollections.observableArrayList(rooms);
            roomTable.setItems(roomList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addBill() {
        Dialog<Bill> dialog = new Dialog<>();
        dialog.setTitle("Tambah Tagihan");
        dialog.setHeaderText("Masukan Detail Tagihan:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");

        ComboBox<Tenant> idTenantComboBox = new ComboBox<>();
        idTenantComboBox.setPromptText("Pilih Penghuni");
        grid.add(new Label("ID Penghuni:"), 0, 0);
        grid.add(idTenantComboBox, 1, 0);

        TextField nameField = new TextField();
        nameField.setEditable(false);
        nameField.setPromptText("Nama");
        grid.add(new Label("Nama:"), 0, 1);
        grid.add(nameField, 1, 1);

        TextField idRoomField = new TextField();
        idRoomField.setEditable(false);
        idRoomField.setPromptText("ID Kamar");
        grid.add(new Label("ID Kamar:"), 0, 2);
        grid.add(idRoomField, 1, 2);

        TextField roomPriceField = new TextField();
        roomPriceField.setEditable(false);
        roomPriceField.setPromptText("Harga Kamar");
        grid.add(new Label("Harga Kamar:"), 0, 3);
        grid.add(roomPriceField, 1, 3);

        DatePicker paymentDatePicker = new DatePicker();
        paymentDatePicker.setEditable(false); // Menonaktifkan pengeditan
        grid.add(new Label("Tanggal Bayar:"), 0, 4);
        grid.add(paymentDatePicker, 1, 4);

        TextField amountPaidField = new TextField();
        amountPaidField.setEditable(false); // Menonaktifkan pengeditan
        amountPaidField.setPromptText("Harga Bayar");
        grid.add(new Label("Harga Bayar:"), 0, 5);
        grid.add(amountPaidField, 1, 5);

        try {
            List<Tenant> tenants = tenantOperations.getAllTenants();
            ObservableList<Tenant> tenantList = FXCollections.observableArrayList(tenants);
            idTenantComboBox.setItems(tenantList);
            idTenantComboBox.setCellFactory(param -> new ListCell<Tenant>() {
                @Override
                protected void updateItem(Tenant tenant, boolean empty) {
                    super.updateItem(tenant, empty);
                    if (empty || tenant == null) {
                        setText(null);
                    } else {
                        setText(tenant.getIdTenant() + " - " + tenant.getName());
                    }
                }
            });
            idTenantComboBox.setButtonCell(new ListCell<Tenant>() {
                @Override
                protected void updateItem(Tenant tenant, boolean empty) {
                    super.updateItem(tenant, empty);
                    if (empty || tenant == null) {
                        setText("Select Penghuni");
                    } else {
                        setText(tenant.getIdTenant() + " - " + tenant.getName());
                    }
                }
            });
            idTenantComboBox.setOnAction(e -> {
                Tenant selectedTenant = idTenantComboBox.getValue();
                if (selectedTenant != null) {
                    nameField.setText(selectedTenant.getName());
                    idRoomField.setText(String.valueOf(selectedTenant.getIdRoom()));
                    roomPriceField.setText(String.valueOf(selectedTenant.getRoomPrice()));
                    paymentDatePicker.setValue(selectedTenant.getPayDate().toLocalDate()); // Mengisi Payment Date
                    amountPaidField.setText(String.valueOf(selectedTenant.getRoomPrice())); // Mengisi Amount Paid
                } else {
                    nameField.clear();
                    idRoomField.clear();
                    roomPriceField.clear();
                    paymentDatePicker.setValue(null); // Membersihkan Payment Date
                    amountPaidField.clear(); // Membersihkan Amount Paid
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

        dialog.getDialogPane().setContent(grid);

        ButtonType addButton = new ButtonType("Tambah", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                Tenant selectedTenant = idTenantComboBox.getValue();
                if (selectedTenant != null) {
                    try {
                        return new Bill(
                            0,
                            selectedTenant.getIdTenant(),
                            selectedTenant.getName(),
                            selectedTenant.getIdRoom(),
                            selectedTenant.getRoomPrice(),
                            Date.valueOf(paymentDatePicker.getValue()), // Mengambil Payment Date dari DatePicker
                            Double.parseDouble(amountPaidField.getText()) // Mengambil Amount Paid dari TextField
                        );
                    } catch (Exception ex) {
                        showAlert("Kesalahan", "Gagal Memasukan.");
                    }
                } else {
                    showAlert("Kesalahan", "Silahkan pilih penghuni.");
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(bill -> {
            try {
                billOperations.addBill(bill);
                billTable.getItems().add(bill);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void updateBill() {
        Bill selectedBill = billTable.getSelectionModel().getSelectedItem();
        if (selectedBill != null) {
            Dialog<Bill> dialog = new Dialog<>();
            dialog.setTitle("Edit Tagihan");
            dialog.setHeaderText("Edit Detail Tagihan:");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            grid.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");

            ComboBox<Tenant> idTenantComboBox = new ComboBox<>();
            idTenantComboBox.setPromptText("Pilih Penghuni");
            grid.add(new Label("ID Penghuni:"), 0, 0);
            grid.add(idTenantComboBox, 1, 0);

            TextField nameField = new TextField();
            nameField.setEditable(false);
            nameField.setPromptText("Nama");
            grid.add(new Label("Nama:"), 0, 1);
            grid.add(nameField, 1, 1);

            TextField idRoomField = new TextField();
            idRoomField.setEditable(false);
            idRoomField.setPromptText("ID Kamar");
            grid.add(new Label("ID Kamar:"), 0, 2);
            grid.add(idRoomField, 1, 2);

            TextField roomPriceField = new TextField();
            roomPriceField.setEditable(false);
            roomPriceField.setPromptText("Harga Kamar");
            grid.add(new Label("Harga Kamar:"), 0, 3);
            grid.add(roomPriceField, 1, 3);

            DatePicker paymentDatePicker = new DatePicker(selectedBill.getPaymentDate().toLocalDate());
            grid.add(new Label("Tanggal Bayar:"), 0, 4);
            grid.add(paymentDatePicker, 1, 4);

            TextField amountPaidField = new TextField(String.valueOf(selectedBill.getAmountPaid()));
            amountPaidField.setPromptText("Harga Bayar");
            grid.add(new Label("Harga Bayar:"), 0, 5);
            grid.add(amountPaidField, 1, 5);

            try {
                List<Tenant> tenants = tenantOperations.getAllTenants();
                ObservableList<Tenant> tenantList = FXCollections.observableArrayList(tenants);
                idTenantComboBox.setItems(tenantList);
                idTenantComboBox.setCellFactory(param -> new ListCell<Tenant>() {
                    @Override
                    protected void updateItem(Tenant tenant, boolean empty) {
                        super.updateItem(tenant, empty);
                        if (empty || tenant == null) {
                            setText(null);
                        } else {
                            setText(tenant.getIdTenant() + " - " + tenant.getName());
                        }
                    }
                });
                idTenantComboBox.setButtonCell(new ListCell<Tenant>() {
                    @Override
                    protected void updateItem(Tenant tenant, boolean empty) {
                        super.updateItem(tenant, empty);
                        if (empty || tenant == null) {
                            setText("Pilih Penghuni");
                        } else {
                            setText(tenant.getIdTenant() + " - " + tenant.getName());
                        }
                    }
                });
                idTenantComboBox.setOnAction(e -> {
                    Tenant selectedTenant = idTenantComboBox.getValue();
                    if (selectedTenant != null) {
                        nameField.setText(selectedTenant.getName());
                        idRoomField.setText(String.valueOf(selectedTenant.getIdRoom()));
                        roomPriceField.setText(String.valueOf(selectedTenant.getRoomPrice()));
                    } else {
                        nameField.clear();
                        idRoomField.clear();
                        roomPriceField.clear();
                    }
                });
                idTenantComboBox.setValue(tenantOperations.getTenantById(selectedBill.getIdTenant()));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            dialog.getDialogPane().setContent(grid);

            ButtonType updateButton = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(updateButton, cancelButton);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButton) {
                    Tenant selectedTenant = idTenantComboBox.getValue();
                    if (selectedTenant != null) {
                        try {
                            java.sql.Date paymentDate = Date.valueOf(paymentDatePicker.getValue());
                            Bill updatedBill = new Bill(selectedBill.getIdTransaction(), selectedTenant.getIdTenant(), selectedTenant.getName(), selectedTenant.getIdRoom(), selectedTenant.getRoomPrice(), paymentDate, Double.parseDouble(amountPaidField.getText()));
                            return updatedBill;
                        } catch (Exception ex) {
                            showAlert("Kesalahan", "Gagal Memasukan.");
                        }
                    } else {
                        showAlert("Kesalahan", "Silahkan pilih penghuni.");
                    }
                }
                return null;
            });

            dialog.showAndWait().ifPresent(updatedBill -> {
                try {
                    billOperations.updateBill(updatedBill);
                    billTable.getItems().set(billTable.getItems().indexOf(selectedBill), updatedBill);

                    // Update checkout date in tenant table
                    tenantOperations.updateTenantPayDate(updatedBill.getIdTenant(), updatedBill.getPaymentDate());

                    // Refresh tenant table if necessary
                    // tenantTable.refresh();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } else {
            showAlert("Kesalahan", "Pilih tagihan yang akan diedit.");
        }
    }

    private void deleteBill() {
        Bill selectedBill = billTable.getSelectionModel().getSelectedItem();
        if (selectedBill != null) {
            try {
                billOperations.deleteBill(selectedBill.getIdTransaction());
                billTable.getItems().remove(selectedBill);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Kesalahan", "Pilih tagihan yang akan dihapus.");
        }
    }

    private void deleteMessage() {
        Message selectedMessage = messageTable.getSelectionModel().getSelectedItem();
        if (selectedMessage != null) {
            try {
                messageOperations.deleteMessage(selectedMessage.getIdMessage());
                messageTable.getItems().remove(selectedMessage);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Kesalahan", "Pilih pesan yang akan dihapus.");
        }
    }

    private void addAdmin() {
        Dialog<Admin> dialog = new Dialog<>();
        dialog.setTitle("Tambah Informasi Admin");
        dialog.setHeaderText("Masukan Detail Admin:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");

        TextField kostAddressField = new TextField();
        kostAddressField.setPromptText("Alamat Kost");
        grid.add(new Label("Alamat Kost:"), 0, 0);
        grid.add(kostAddressField, 1, 0);

        TextField accountNameField = new TextField();
        accountNameField.setPromptText("Nama Akun");
        grid.add(new Label("Nama Akun:"), 0, 1);
        grid.add(accountNameField, 1, 1);

        TextField accountNumberField = new TextField();
        accountNumberField.setPromptText("Nomor Akun");
        grid.add(new Label("Nomor Akun:"), 0, 2);
        grid.add(accountNumberField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        ButtonType addButton = new ButtonType("Tambah", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                return new Admin(0, kostAddressField.getText(), accountNameField.getText(), accountNumberField.getText());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(admin -> {
            try {
                adminOperations.addAdmin(admin);
                adminTable.getItems().add(admin);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void updateAdmin() {
        Admin selectedAdmin = adminTable.getSelectionModel().getSelectedItem();
        if (selectedAdmin != null) {
            Dialog<Admin> dialog = new Dialog<>();
            dialog.setTitle("Edit Informasi Admin");
            dialog.setHeaderText("Edit Detail Admin:");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            grid.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");

            TextField kostAddressField = new TextField(selectedAdmin.getkostAddress());
            kostAddressField.setPromptText("Alamat Kost");
            grid.add(new Label("Alamat Kost:"), 0, 0);
            grid.add(kostAddressField, 1, 0);

            TextField accountNameField = new TextField(selectedAdmin.getaccountName());
            accountNameField.setPromptText("Nama Akun");
            grid.add(new Label("Nama Akun:"), 0, 1);
            grid.add(accountNameField, 1, 1);

            TextField accountNumberField = new TextField(selectedAdmin.getaccountNumber());
            accountNumberField.setPromptText("Nomor Akun");
            grid.add(new Label("Nomor Akun:"), 0, 2);
            grid.add(accountNumberField, 1, 2);

            dialog.getDialogPane().setContent(grid);

            ButtonType updateButton = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(updateButton, cancelButton); 

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButton) {
                    return new Admin(selectedAdmin.getIdAdmin(), kostAddressField.getText(), accountNameField.getText(), accountNumberField.getText());
                }
                return null;
            });

            dialog.showAndWait().ifPresent(updatedAdmin -> {
                try {
                    adminOperations.updateAdmin(updatedAdmin);
                    adminTable.getItems().set(adminTable.getItems().indexOf(selectedAdmin), updatedAdmin);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } else {
            showAlert("Kesalahan", "Pilih admin yang akan diedit.");
        }
    }

    private void deleteAdmin() {
        Admin selectedAdmin = adminTable.getSelectionModel().getSelectedItem();
        if (selectedAdmin != null) {
            try {
                adminOperations.deleteAdmin(selectedAdmin.getIdAdmin());
                adminTable.getItems().remove(selectedAdmin);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Kesalahan", "Pilih admin yang akan hapus.");
        }
    }

    private void addLogin() {
        Dialog<Login> dialog = new Dialog<>();
        dialog.setTitle("Tambah Akun");
        dialog.setHeaderText("Masukan Detail Akun:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        grid.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");

        TextField nameField = new TextField();
        nameField.setPromptText("Nama");
        grid.add(new Label("Nama:"), 0, 0);
        grid.add(nameField, 1, 0);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        grid.add(new Label("Username:"), 0, 1);
        grid.add(usernameField, 1, 1);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        grid.add(new Label("Password:"), 0, 2);
        grid.add(passwordField, 1, 2);

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Admin", "Tenant");
        roleComboBox.setPromptText("Role");
        grid.add(new Label("Role:"), 0, 3);
        grid.add(roleComboBox, 1, 3);

        dialog.getDialogPane().setContent(grid);

        ButtonType addButton = new ButtonType("Tambah", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                if (nameField.getText() == null || nameField.getText().isEmpty()) {
                    showAlert("Gagal memasukan", "Nama tidak boleh null atau kosong");
                    return null;
                }
                if (usernameField.getText() == null || usernameField.getText().isEmpty()) {
                    showAlert("Gagal memasukan", "Username tidak boleh null atau kosong");
                    return null;
                }
                if (passwordField.getText() == null || passwordField.getText().isEmpty()) {
                    showAlert("Gagal memasukan", "Password tidak boleh null atau kosong");
                    return null;
                }
                if (roleComboBox.getValue() == null) {
                    showAlert("Gagal memasukan", "Role tidak boleh null atau kosong");
                    return null;
                }
                return new Login(0, nameField.getText(), usernameField.getText(), passwordField.getText(), roleComboBox.getValue());
            }
            return null;
        });

        dialog.showAndWait().ifPresent(login -> {
            try {
                loginOperations.addLogin(login);
                loginTable.getItems().add(login);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void updateLogin() {
        Login selectedLogin = loginTable.getSelectionModel().getSelectedItem();
        if (selectedLogin != null) {
            Dialog<Login> dialog = new Dialog<>();
            dialog.setTitle("Edit Akun");
            dialog.setHeaderText("Edit Detail Akun:");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            grid.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000;");

            TextField nameField = new TextField(selectedLogin.getName());
            nameField.setPromptText("Nama");
            grid.add(new Label("Nama:"), 0, 0);
            grid.add(nameField, 1, 0);

            TextField usernameField = new TextField(selectedLogin.getUsername());
            usernameField.setPromptText("Username");
            grid.add(new Label("Username:"), 0, 1);
            grid.add(usernameField, 1, 1);

            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");
            grid.add(new Label("Password:"), 0, 2);
            grid.add(passwordField, 1, 2);

            ComboBox<String> roleComboBox = new ComboBox<>();
            roleComboBox.getItems().addAll("Admin", "Tenant");
            roleComboBox.setValue(selectedLogin.getRole());
            roleComboBox.setPromptText("Role");
            grid.add(new Label("Role:"), 0, 3);
            grid.add(roleComboBox, 1, 3);

            dialog.getDialogPane().setContent(grid);

            ButtonType updateButton = new ButtonType("Edit", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(updateButton, cancelButton);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButton) {
                    if (nameField.getText() == null || nameField.getText().isEmpty()) {
                        showAlert("Gagal memasukan", "Name tidak boleh null atau kosong");
                        return null;
                    }
                    if (usernameField.getText() == null || usernameField.getText().isEmpty()) {
                        showAlert("Gagal memasukan", "Username tidak boleh null atau kosong");
                        return null;
                    }
                    if (passwordField.getText() == null || passwordField.getText().isEmpty()) {
                        showAlert("Gagal memasukan", "Password tidak boleh null atau kosong");
                        return null;
                    }
                    if (roleComboBox.getValue() == null) {
                        showAlert("Gagal memasukan", "Role tidak boleh null atau kosong");
                        return null;
                    }
                    return new Login(selectedLogin.getIdAccount(), nameField.getText(), usernameField.getText(), passwordField.getText(), roleComboBox.getValue());
                }
                return null;
            });

            dialog.showAndWait().ifPresent(updatedLogin -> {
                try {
                    loginOperations.updateLogin(updatedLogin);
                    loginTable.getItems().set(loginTable.getItems().indexOf(selectedLogin), updatedLogin);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } else {
            showAlert("Kesalahan", "Pilih akun yang akan diedit.");
        }
    }

    private void deleteLogin() {
        Login selectedLogin = loginTable.getSelectionModel().getSelectedItem();
        if (selectedLogin != null) {
            try {
                loginOperations.deleteLogin(selectedLogin.getIdAccount());
                loginTable.getItems().remove(selectedLogin);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Kesalahan", "Pilih akun yang akan dihapus.");
        }
    }

    private void logout() {
        primaryStage.setScene(new Scene(new LoginView(primaryStage).getView()));
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
