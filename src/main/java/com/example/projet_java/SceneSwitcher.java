package com.example.projet_java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public static void switchScene(Stage stage, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(SceneSwitcher.class.getResource("/com/example/projet_java/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
