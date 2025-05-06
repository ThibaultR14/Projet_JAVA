package com.example.JAVAcontroller;

import com.example.projet_java.SceneSwitcher;
import com.example.projet_java.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
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
    private HBox headerBar;



    @FXML
    public void initialize() {

        if (Session.estConnecte()) {
            // Cacher les boutons de connexion et d'inscription
            connexionButton.setVisible(false);
            inscriptionButton.setVisible(false);

            // Ajouter le bouton "Mon Compte"
            Button monCompteButton = new Button("Mon Compte");
            monCompteButton.getStyleClass().add("button-secondary");
            monCompteButton.setOnAction(e -> {
                SceneSwitcher.switchScene(
                        (Stage) monCompteButton.getScene().getWindow(),
                        "/com/example/projet_java/mon-compte.fxml",
                        "Mon Compte"
                );
            });

            if (connexionButton.getParent() instanceof HBox hbox) {
                hbox.getChildren().add(monCompteButton);
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
            // ✅ Ajout du CSS ici :
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
        Stage stage = (Stage) connexionButton.getScene().getWindow(); // ou un autre composant de la scène

        if (!Session.estConnecte()) {
            // Rediriger vers la page de connexion si non connecté
            SceneSwitcher.switchScene(stage, "/com/example/projet_java/connexion.fxml", "Connexion");
        } else {
            // Sinon, aller vers ajout-hebergement
            SceneSwitcher.switchScene(stage, "/com/example/projet_java/ajout-hebergement.fxml", "Mettre en ligne un hébergement");
        }
    }



}
