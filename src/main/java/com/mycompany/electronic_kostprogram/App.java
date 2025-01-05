package com.mycompany.electronic_kostprogram;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.*;
import static javafx.application.Application.launch;

// Abstraction: Abstract class representing a user
abstract class User {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public abstract void dashboard(Stage stage);
}

// Inheritance: Admin is a subclass of User
class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public void dashboard(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label lblTitle = new Label("Admin Dashboard");
        Button btnAddTenant = new Button("Add Tenant");
        Button btnLogout = new Button("Logout");

        btnAddTenant.setOnAction(e -> addTenant(stage));
        btnLogout.setOnAction(e -> App.showLogin(stage));

        root.getChildren().addAll(lblTitle, btnAddTenant, btnLogout);

        stage.setScene(new Scene(root, 400, 300));
    }

    private void addTenant(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label lblTitle = new Label("Add Tenant");
        TextField tfName = new TextField();
        tfName.setPromptText("Name");
        TextField tfUsername = new TextField();
        tfUsername.setPromptText("Username");
        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Password");
        Button btnSave = new Button("Save");
        Button btnBack = new Button("Back");

        btnSave.setOnAction(e -> {
            String name = tfName.getText();
            String username = tfUsername.getText();
            String password = pfPassword.getText();
            boolean success = DatabaseConnection.addTenant(name, username, password);
            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Tenant added successfully");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to add tenant. Check the logs for details.");
                alert.show();
            }
        });

        btnBack.setOnAction(e -> dashboard(stage));

        root.getChildren().addAll(lblTitle, tfName, tfUsername, pfPassword, btnSave, btnBack);

        stage.setScene(new Scene(root, 400, 300));
    }
}

// Inheritance: Tenant is a subclass of User
class Tenant extends User {

    public Tenant(String username, String password) {
        super(username, password);
    }

    @Override
    public void dashboard(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label lblTitle = new Label("Tenant Dashboard");
        Button btnLogout = new Button("Logout");

        btnLogout.setOnAction(e -> App.showLogin(stage));

        root.getChildren().addAll(lblTitle, btnLogout);

        stage.setScene(new Scene(root, 400, 300));
    }
}

// Encapsulation: Database connection and operations are encapsulated in a separate class

public class App extends Application {

    public static void showLogin(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label lblTitle = new Label("Login");
        TextField tfUsername = new TextField();
        tfUsername.setPromptText("Username");
        PasswordField pfPassword = new PasswordField();
        pfPassword.setPromptText("Password");
        Button btnLogin = new Button("Login");

        btnLogin.setOnAction(e -> {
            String username = tfUsername.getText();
            String password = pfPassword.getText();
            User user = DatabaseConnection.authenticate(username, password);
            if (user != null) {
                user.dashboard(stage);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid credentials");
                alert.show();
            }
        });

        root.getChildren().addAll(lblTitle, tfUsername, pfPassword, btnLogin);

        stage.setScene(new Scene(root, 400, 300));
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Kost Management System");
        showLogin(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


