package com.mycompany.electronic_kostprogram;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;
import login.LoginView;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginView loginView = new LoginView();
        Scene scene = new Scene(loginView.getView(primaryStage), 400, 400);
        
        primaryStage.setTitle("Login System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
