package com.example.JAVAcontroller;

import com.example.JAVAdao.UtilisateurDAO;
import com.example.JAVAmodel.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InscriptionController {

    @FXML private TextField nomField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private CheckBox adminCheckBox;
    @FXML private Button inscrireButton;

    private UtilisateurDAO utilisateurDAO;

    public void initialize() {
        try {
            // Connexion à la base de données (à adapter selon ta config)
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/booking", "root", "");
            utilisateurDAO = new UtilisateurDAO(connection);
        } catch (SQLException e) {
            afficherAlerte("Erreur de connexion à la base de données : " + e.getMessage());
        }

        inscrireButton.setOnAction(e -> inscrireUtilisateur());
    }

    private void inscrireUtilisateur() {
        String nom = nomField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        boolean isAdmin = adminCheckBox.isSelected();

        if (nom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            afficherAlerte("Veuillez remplir tous les champs !");
            return;
        }

        Utilisateur nouvelUtilisateur = new Utilisateur(0, nom, email, password, isAdmin, null);
        int id = utilisateurDAO.ajouterUtilisateur(nouvelUtilisateur);

        if (id > 0) {
            afficherAlerte("Utilisateur ajouté avec succès (ID : " + id + ")");
            viderChamps();
        } else {
            afficherAlerte("Erreur lors de l'ajout de l'utilisateur.");
        }
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void viderChamps() {
        nomField.clear();
        emailField.clear();
        passwordField.clear();
        adminCheckBox.setSelected(false);
    }
}
