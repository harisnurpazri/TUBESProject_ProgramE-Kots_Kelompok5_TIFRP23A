package TenantUser;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import login.LoginView;

public class TenantView {

    public void show(Stage stage) {
        VBox tenantRoot = new VBox(20);
        tenantRoot.setAlignment(Pos.CENTER);
        tenantRoot.setStyle("-fx-background-color: #dff9fb; -fx-padding: 40px;");

        Label title = new Label("Tenant Dashboard");
        title.setFont(new Font("Arial Bold", 24));
        title.setTextFill(Color.DARKGREEN);

        Label welcomeLabel = new Label("Selamat datang di dashboard penghuni!");
        welcomeLabel.setFont(new Font("Arial", 16));

        // Tombol Logout
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> {
            LoginView loginView = new LoginView();
            stage.setScene(new Scene(loginView.getView(stage), 400, 400));
            stage.setTitle("Login System");
        });

        tenantRoot.getChildren().addAll(title, welcomeLabel, logoutButton);

        stage.setScene(new Scene(tenantRoot, 600, 400));
        stage.setTitle("Tenant Dashboard");
        stage.show();
    }
}
