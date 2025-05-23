package com.example.projet_java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class BookingApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            URL url = getClass().getResource("/com/example/projet_java/accueil.fxml");
            System.out.println("URL FXML = " + url);

            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/example/projet_java/style.css").toExternalForm());
            primaryStage.setTitle("Page d'Accueil");
            primaryStage.setScene(scene);
            primaryStage.setWidth(1000);
            primaryStage.setHeight(600);

            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
