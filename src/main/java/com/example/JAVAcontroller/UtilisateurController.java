package com.example.JAVAcontroller;

import com.example.JAVAdao.UtilisateurDAO;
import com.example.JAVAmodel.Utilisateur;
import com.example.projet_java.SceneSwitcher;
import com.example.projet_java.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UtilisateurController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField nomField;     // présent pour inscription
    @FXML private TextField prenomField;  // nouveau champ pour inscription

    @FXML
    public void onConnexion() {
        String email = emailField.getText();
        String password = passwordField.getText();

        Utilisateur user = UtilisateurDAO.getUtilisateurParEmailEtMotDePasse(email, password);
        if (user != null) {
            Session.setUtilisateur(user); // On stocke l'utilisateur connecté
            SceneSwitcher.switchScene(
                    (Stage) emailField.getScene().getWindow(),
                    "/com/example/projet_java/accueil.fxml",
                    "Accueil"
            );
        } else {
            System.out.println("Échec de connexion.");
            // Optionnel : alerte utilisateur
        }
    }

    @FXML
    public void onInscription() {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        boolean success = UtilisateurDAO.ajouterUtilisateur(
                new Utilisateur(nom, prenom, email, password, false)
        );

        if (success) {
            System.out.println("Inscription réussie !");
            SceneSwitcher.switchScene(
                    (Stage) emailField.getScene().getWindow(),
                    "/com/example/projet_java/connexion.fxml",
                    "Connexion"
            );
        } else {
            System.out.println("Erreur : email déjà utilisé ?");
        }
    }
    public void goToInscription(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projet_java/inscription.fxml"));
            Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/com/example/projet_java/style.css").toExternalForm());

            stage.setScene(scene);
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de charger la page d'inscription.");
        }
    }

    public void goToConnexion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projet_java/connexion.fxml"));
            Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/com/example/projet_java/style.css").toExternalForm());

            stage.setScene(scene);
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de charger la page de connexion.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
