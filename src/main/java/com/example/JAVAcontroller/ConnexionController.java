package com.example.JAVAcontroller;

import com.example.JAVAdao.UtilisateurDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button connexionButton;

    private UtilisateurDAO utilisateurDAO;

    public void initialize() {
        try {
            // Connexion à la base de données
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/booking", "root", "");
            utilisateurDAO = new UtilisateurDAO(connection);
        } catch (SQLException e) {
            afficherAlerte("Erreur de connexion à la base de données : " + e.getMessage());
        }

        connexionButton.setOnAction(e -> connecterUtilisateur());
    }

    private void connecterUtilisateur() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            afficherAlerte("Veuillez remplir tous les champs !");
            return;
        }

        // Vérifie si l'utilisateur existe avec les bons identifiants
        boolean utilisateurExiste = utilisateurDAO.verifierUtilisateur(email, password);

        if (utilisateurExiste) {
            afficherAlerte("Connexion réussie !");
        } else {
            afficherAlerte("Échec de connexion. Email ou mot de passe incorrect.");
        }

        viderChamps();
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void viderChamps() {
        emailField.clear();
        passwordField.clear();
    }
}
