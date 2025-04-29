package com.example.JAVAcontroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AccueilController {

    @FXML
    private TextField villeField;

    @FXML
    private DatePicker dateArriveePicker;

    @FXML
    private DatePicker dateDepartPicker;

    @FXML
    private Button rechercherButton;

    @FXML
    private Button connexionButton;

    @FXML
    private Button inscriptionButton;

    @FXML
    public void onRechercherClicked() {
        try {
            // Charger le fichier FXML de la page de recherche
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projet_java/recherche-hebergement.fxml"));
            Parent root = loader.load();

            com.example.JAVAcontroller.RechercheHebergementController controller = loader.getController();

            controller.initData(
                    villeField.getText(),
                    dateArriveePicker.getValue(),
                    dateDepartPicker.getValue()
            );

            Stage stage = (Stage) rechercherButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Recherche d'Hébergements");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onConnexionClicked() {
        System.out.println("Clic sur Connexion");
        // À faire : ouvrir la page de connexion
    }

    @FXML
    public void onInscriptionClicked() {
        System.out.println("Clic sur Inscription");
        // À faire : ouvrir la page d'inscription
    }
}
