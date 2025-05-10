package com.example.JAVAcontroller;

import com.example.JAVAmodel.Utilisateur;
import com.example.projet_java.SceneSwitcher;
import com.example.projet_java.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HeaderController {

    @FXML
    private Button navButton;

    @FXML
    public void initialize() {
        Utilisateur user = Session.getUtilisateur();

        if (user != null) {
            navButton.setText("Mon Compte");
        } else {
            navButton.setText("Connexion");
        }
    }

    @FXML
    public void onNavClicked() {
        Stage stage = (Stage) navButton.getScene().getWindow();

        if (Session.estConnecte()) {
            SceneSwitcher.switchScene(stage, "/com/example/projet_java/mon-compte.fxml", "Mon Compte");
        } else {
            SceneSwitcher.switchScene(stage, "/com/example/projet_java/connexion.fxml", "Connexion");
        }
    }

    @FXML
    public void onLogoClicked() {
        SceneSwitcher.switchScene(
                (Stage) navButton.getScene().getWindow(),
                "/com/example/projet_java/accueil.fxml",
                "Accueil"
        );
    }
    @FXML
    public void onUploadClicked() {
        Stage stage = (Stage) navButton.getScene().getWindow(); // ou un autre composant de la scène

        if (!Session.estConnecte()) {
            // Rediriger vers la page de connexion si non connecté
            SceneSwitcher.switchScene(stage, "/com/example/projet_java/connexion.fxml", "Connexion");
        }
    }



}
