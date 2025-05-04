package com.example.projet_java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class MainUtilisateur extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Récupère le FXML d'inscription dans resources/com/example/projet_java/Inscription.fxml
            URL fxmlUrl = getClass().getResource(
                    "/com/example/projet_java/Inscription.fxml"
            );
            System.out.println("URL Inscription.fxml = " + fxmlUrl);

            Parent root = FXMLLoader.load(fxmlUrl);
            Scene scene = new Scene(root, 800, 600);

            primaryStage.setTitle("Formulaire d'inscription");
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