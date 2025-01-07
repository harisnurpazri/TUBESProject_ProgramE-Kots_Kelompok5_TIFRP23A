package Administrator;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import login.LoginView;

public class AdminView {
    public void show(Stage stage) {
        VBox adminRoot = new VBox(20);
        adminRoot.setAlignment(Pos.CENTER);
        adminRoot.setStyle("-fx-background-color: #e8f4ff; -fx-padding: 40px;");

        Label adminLabel = new Label("Admin Dashboard");
        adminLabel.setFont(new Font("Arial Bold", 24));
        adminLabel.setTextFill(Color.DARKGREEN);

        // Input Fields
        TextField idAccountField = new TextField();
        idAccountField.setPromptText("ID Account");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button addUserButton = new Button("Add User");
        addUserButton.setOnAction(event -> {
            String idAccount = idAccountField.getText();
            String name = nameField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (idAccount.isEmpty() || name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                showError("Semua field harus diisi!");
                return;
            }

            try {
                int id = Integer.parseInt(idAccount);
                login.LoginOperation loginOperation = new login.LoginOperation();
                loginOperation.addUser(id, name, username, password);
                showSuccess("Pengguna baru berhasil ditambahkan!");

                // Bersihkan field setelah penambahan
                idAccountField.clear();
                nameField.clear();
                usernameField.clear();
                passwordField.clear();
            } catch (Exception e) {
                showError("ID Account harus berupa angka.");
            }
        });

        // Tombol Logout
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> {
            LoginView loginView = new LoginView();
            stage.setScene(new Scene(loginView.getView(stage), 400, 400));
            stage.setTitle("Login System");
        });

        adminRoot.getChildren().addAll(
            adminLabel,
            idAccountField,
            nameField,
            usernameField,
            passwordField,
            addUserButton,
            logoutButton
        );

        stage.setScene(new Scene(adminRoot, 600, 500));
    }

    private void showSuccess(String message) {
        showAlert(Alert.AlertType.INFORMATION, "Success", message);
    }

    private void showError(String message) {
        showAlert(Alert.AlertType.ERROR, "Error", message);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
