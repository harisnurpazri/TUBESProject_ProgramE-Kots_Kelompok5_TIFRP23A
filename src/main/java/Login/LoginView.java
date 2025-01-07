package login;

import Administrator.AdminView;
import TenantUser.TenantView; 
import TenantUser.TenantView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;

public class LoginView {
    private LoginOperation loginOperations;

    public LoginView() {
        try {
            loginOperations = new LoginOperation();
        } catch (Exception e) {
            e.printStackTrace();
            loginOperations = null;
            showError("Database connection error.");
        }
    }

    public VBox getView(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f5f5f5; -fx-padding: 40px;");

        Label title = new Label("Login System");
        title.setFont(new Font("Arial Bold", 24));
        title.setTextFill(Color.DARKBLUE);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> handleLogin(usernameField.getText(), passwordField.getText(), primaryStage));

        root.getChildren().addAll(title, usernameField, passwordField, loginButton);
        return root;
    }

    private void handleLogin(String username, String password, Stage stage) {
        if (loginOperations == null) {
            showError("Database connection is not established. Please restart the application.");
            return;
        }

        if (username.isEmpty() || password.isEmpty()) {
            showError("Username dan password tidak boleh kosong.");
            return;
        }

        String role = loginOperations.validateLogin(username, password);

        if (role == null) {
            showError("Login gagal. Periksa username atau password.");
        } else if ("admin".equals(role)) {
            showSuccess("Welcome Admin!");
            AdminView adminview = new AdminView();
            adminview.show(stage);
        } else if ("user".equals(role)) {
            showSuccess("Welcome Penghuni!");
            TenantView tenantView = new TenantView();
            tenantView.show(stage);
        }
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
