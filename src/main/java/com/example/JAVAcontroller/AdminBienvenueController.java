package com.example.JAVAcontroller;

import com.example.projet_java.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import com.example.projet_java.SceneSwitcher;
import com.example.projet_java.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.io.IOException;

public class AdminBienvenueController {
    @FXML private Button logoutButton;

    @FXML
    private void handleHebergements(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneSwitcher.switchScene(stage, "/com/example/projet_java/adminhebergement.fxml", "Liste des hébergements");
    }

    @FXML
    public void handleReservations(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneSwitcher.switchScene(stage, "/com/example/projet_java/adminreservation.fxml", "Réservations");
    }

    @FXML
    private void handleStats() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projet_java/statistics-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 800, 600);

            scene.getStylesheets().add(getClass().getResource("/com/example/projet_java/style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setTitle("Statistiques");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleReduction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneSwitcher.switchScene(stage, "/com/example/projet_java/adminreduction.fxml", "Réduction");
    }

    @FXML
    private void handleLogout() {
        javafx.stage.Stage stage = (javafx.stage.Stage) logoutButton.getScene().getWindow();
        com.example.projet_java.SceneSwitcher.switchScene(stage, "/com/example/projet_java/mon-compte.fxml", "Mon Compte");
    }

}
