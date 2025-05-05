package com.example.JAVAcontroller;

import com.example.projet_java.SceneSwitcher;
import com.example.projet_java.Session;
import com.example.JAVAdao.UtilisateurDAO;
import com.example.JAVAmodel.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MonCompteController {

    @FXML private Label titreLabel;
    @FXML private Label roleLabel;

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML private Button modifierButton;
    @FXML private Button validerButton;

    @FXML
    public void initialize() {
        Utilisateur u = Session.getUtilisateur();
        if (u != null) {
            titreLabel.setText(u.getPrenom() + " " + u.getNom());
            emailField.setText(u.getEmail());
            passwordField.setText(u.getPassword());
            roleLabel.setText(u.isAdmin() ? "Administrateur" : "Client");

            // Champs désactivés par défaut
            emailField.setEditable(false);
            passwordField.setEditable(false);
            validerButton.setVisible(false);
        }
    }

    @FXML
    public void onRetourAccueil() {
        SceneSwitcher.switchScene(
                (Stage) titreLabel.getScene().getWindow(),
                "/com/example/projet_java/accueil.fxml",
                "Accueil"
        );
    }

    @FXML
    public void onModifierClicked() {
        emailField.setEditable(true);
        passwordField.setEditable(true);
        validerButton.setVisible(true);
        modifierButton.setDisable(true);
    }

    @FXML
    public void onValiderClicked() {
        Utilisateur u = Session.getUtilisateur();

        String newEmail = emailField.getText();
        String newPassword = passwordField.getText();

        // (Optionnel) : ajouter ici des vérifications

        u.setEmail(newEmail);
        u.setPassword(newPassword);

        boolean success = UtilisateurDAO.mettreAJourUtilisateur(u);
        if (success) {
            emailField.setEditable(false);
            passwordField.setEditable(false);
            validerButton.setVisible(false);
            modifierButton.setDisable(false);
            System.out.println("Profil mis à jour avec succès.");
        } else {
            System.out.println("Erreur lors de la mise à jour.");
        }
    }

    @FXML
    public void onDeconnexion() {
        Session.deconnexion(); // Supprime l'utilisateur actif
        SceneSwitcher.switchScene(
                (Stage) titreLabel.getScene().getWindow(),
                "/com/example/projet_java/accueil.fxml",
                "Accueil"
        );
    }

}
