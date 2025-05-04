package com.example.projet_java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainConnexion extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            URL fxmlUrl = getClass().getResource(
                    "/com/example/projet_java/Connexion.fxml"
            );
            System.out.println("URL Connexion.fxml = " + fxmlUrl);

            Parent root = FXMLLoader.load(fxmlUrl);
            Scene scene = new Scene(root, 800, 600);

            primaryStage.setTitle("Connexion");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}