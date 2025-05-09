package com.example.JAVAcontroller;

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

public class AccueilController {

    @FXML private TextField villeField;
    @FXML private DatePicker dateArriveePicker;
    @FXML private DatePicker dateDepartPicker;
    @FXML private Button rechercherButton;
    @FXML private Button connexionButton;
    @FXML private Button inscriptionButton;
    @FXML private Button uploadButton;
    @FXML private HBox headerBar;

    @FXML
    public void initialize() {
        if (Session.estConnecte()) {
            connexionButton.setVisible(false);
            inscriptionButton.setVisible(false);

            // 🔹 Bouton Mon Compte
            Button monCompteButton = new Button("Mon Compte");
            monCompteButton.getStyleClass().add("header-button");

            monCompteButton.getStyleClass().add("button-secondary");
            monCompteButton.setMinWidth(Region.USE_COMPUTED_SIZE);
            monCompteButton.setMaxWidth(Region.USE_PREF_SIZE);
            monCompteButton.setOnAction(e -> {
                SceneSwitcher.switchScene(
                        (Stage) monCompteButton.getScene().getWindow(),
                        "/com/example/projet_java/mon-compte.fxml",
                        "Mon Compte"
                );
            });

            // 🔹 Bouton Mes Réservations
            Button mesReservationsButton = new Button("Mes Réservations");
            mesReservationsButton.getStyleClass().add("header-button");

            mesReservationsButton.getStyleClass().add("button-secondary");
            mesReservationsButton.setMinWidth(Region.USE_COMPUTED_SIZE);
            mesReservationsButton.setMaxWidth(Region.USE_PREF_SIZE);
            mesReservationsButton.setOnAction(e -> {
                SceneSwitcher.switchScene(
                        (Stage) mesReservationsButton.getScene().getWindow(),
                        "/com/example/projet_java/mes-reservations.fxml",
                        "Mes Réservations"
                );
            });

            // 🔹 Bouton Mes Hébergements
            Button mesHebergementsButton = new Button("Mes Hébergements");
            mesHebergementsButton.getStyleClass().add("header-button");

            mesHebergementsButton.getStyleClass().add("button-secondary");
            mesHebergementsButton.setMinWidth(Region.USE_COMPUTED_SIZE);
            mesHebergementsButton.setMaxWidth(Region.USE_PREF_SIZE);
            mesHebergementsButton.setOnAction(e -> {
                SceneSwitcher.switchScene(
                        (Stage) mesHebergementsButton.getScene().getWindow(),
                        "/com/example/projet_java/mes-hebergements.fxml",
                        "Mes Hébergements"
                );
            });



            if (connexionButton.getParent() instanceof HBox hbox) {
                hbox.getChildren().addAll(monCompteButton, mesReservationsButton, mesHebergementsButton);
            }
        }
    }

    @FXML
    public void onRechercherClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projet_java/recherche-hebergement.fxml"));
            Parent root = loader.load();

            RechercheHebergementController controller = loader.getController();
            controller.initData2(
                    villeField.getText(),
                    dateArriveePicker.getValue(),
                    dateDepartPicker.getValue()
            );

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/example/projet_java/style.css").toExternalForm());

            Stage stage = (Stage) rechercherButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Recherche d'Hébergements");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onConnexionClicked() {
        SceneSwitcher.switchScene(
                (Stage) connexionButton.getScene().getWindow(),
                "/com/example/projet_java/connexion.fxml",
                "Connexion"
        );
    }

    @FXML
    public void onInscriptionClicked() {
        SceneSwitcher.switchScene(
                (Stage) inscriptionButton.getScene().getWindow(),
                "/com/example/projet_java/inscription.fxml",
                "Inscription"
        );
    }

    @FXML
    public void onUploadClicked() {
        Stage stage = (Stage) uploadButton.getScene().getWindow();

        if (!Session.estConnecte()) {
            SceneSwitcher.switchScene(stage, "/com/example/projet_java/connexion.fxml", "Connexion");
        } else {
            SceneSwitcher.switchScene(stage, "/com/example/projet_java/ajout-hebergement.fxml", "Mettre en ligne un hébergement");
        }
    }
}
