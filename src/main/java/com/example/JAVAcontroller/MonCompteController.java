package com.example.JAVAcontroller;

import com.example.projet_java.SceneSwitcher;
import com.example.projet_java.Session;
import com.example.JAVAdao.UtilisateurDAO;
import com.example.JAVAmodel.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MonCompteController {

    @FXML private Label titreLabel;
    @FXML private Label roleLabel;

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML private Button modifierButton;
    @FXML private Button validerButton;

    @FXML private VBox paiementBox;
    @FXML private VBox reductionBox;


    private TextField typeCarteField;
    private TextField numeroCBField;
    private TextField cryptoField;
    private DatePicker expirationPicker;

    private Button modifierCarteButton;
    private Button enregistrerCarteButton;

    @FXML
    public void initialize() {
        Utilisateur u = Session.getUtilisateur();
        if (u != null) {
            titreLabel.setText(u.getPrenom() + " " + u.getNom());
            emailField.setText(u.getEmail());
            passwordField.setText(u.getPassword());
            roleLabel.setText(u.isAdmin() ? "Administrateur" : "Client");

            emailField.setEditable(false);
            passwordField.setEditable(false);
            validerButton.setVisible(false);

            afficherPaiement(u);
            afficherReductions(u);

        }
    }

    private void afficherPaiement(Utilisateur u) {
        paiementBox.getChildren().clear();

        if (u.getNumeroCB() == null || u.getNumeroCB().isBlank()) {
            Button ajouterCarteBtn = new Button("Ajouter une carte bancaire");
            ajouterCarteBtn.getStyleClass().add("button-primary");
            ajouterCarteBtn.setOnAction(e -> activerEditionCarte(u, true));
            paiementBox.getChildren().add(ajouterCarteBtn);
        } else {
            GridPane carteInfos = new GridPane();
            carteInfos.setHgap(15);
            carteInfos.setVgap(10);

            String numMasque = "**** **** **** " + u.getNumeroCB().substring(u.getNumeroCB().length() - 4);
            String cryptoMasque = "***";

            typeCarteField = new TextField(u.getTypeCarte());
            numeroCBField = new TextField(numMasque);
            cryptoField = new TextField(cryptoMasque);
            expirationPicker = new DatePicker(u.getExpiration());

            typeCarteField.setEditable(false);
            numeroCBField.setEditable(false);
            cryptoField.setEditable(false);
            expirationPicker.setDisable(true);

            carteInfos.addRow(0, new Label("Type de carte :"), typeCarteField);
            carteInfos.addRow(1, new Label("Numéro :"), numeroCBField);
            carteInfos.addRow(2, new Label("Cryptogramme :"), cryptoField);
            carteInfos.addRow(3, new Label("Expiration :"), expirationPicker);

            modifierCarteButton = new Button("Modifier la carte");
            modifierCarteButton.getStyleClass().add("button-secondary");
            modifierCarteButton.setOnAction(e -> activerEditionCarte(u, false));

            enregistrerCarteButton = new Button("Enregistrer les modifications");
            enregistrerCarteButton.getStyleClass().add("button-success");
            enregistrerCarteButton.setVisible(false);
            enregistrerCarteButton.setOnAction(e -> enregistrerCarte(u));

            // ✅ Bouton supprimer la carte
            Button supprimerCarteButton = new Button("Supprimer la carte");
            supprimerCarteButton.getStyleClass().add("button-logout");
            supprimerCarteButton.setOnAction(e -> {
                u.setNumeroCB(null);
                u.setCryptogramme(null);
                u.setExpiration(null);
                u.setTypeCarte(null);
                UtilisateurDAO.mettreAJourCarte(u);
                afficherPaiement(u); // Rechargement
            });

            paiementBox.getChildren().addAll(carteInfos, modifierCarteButton, enregistrerCarteButton, supprimerCarteButton);
        }
    }


    private void activerEditionCarte(Utilisateur u, boolean isAjout) {
        if (isAjout) {
            typeCarteField = new TextField();
            numeroCBField = new TextField();
            cryptoField = new TextField();
            expirationPicker = new DatePicker();

            modifierCarteButton = new Button("Annuler");
            modifierCarteButton.getStyleClass().add("button-secondary");
            modifierCarteButton.setOnAction(ev -> afficherPaiement(u));

            enregistrerCarteButton = new Button("Enregistrer");
            enregistrerCarteButton.getStyleClass().add("button-success");
            enregistrerCarteButton.setOnAction(ev -> enregistrerCarte(u));

            paiementBox.getChildren().clear();
            GridPane carteInfos = new GridPane();
            carteInfos.setHgap(15);
            carteInfos.setVgap(10);
            carteInfos.addRow(0, new Label("Type de carte :"), typeCarteField);
            carteInfos.addRow(1, new Label("Numéro :"), numeroCBField);
            carteInfos.addRow(2, new Label("Cryptogramme :"), cryptoField);
            carteInfos.addRow(3, new Label("Expiration :"), expirationPicker);

            paiementBox.getChildren().addAll(carteInfos, modifierCarteButton, enregistrerCarteButton);
        } else {
            numeroCBField.setText(u.getNumeroCB());
            cryptoField.setText(u.getCryptogramme());
        }

        typeCarteField.setEditable(true);
        numeroCBField.setEditable(true);
        cryptoField.setEditable(true);
        expirationPicker.setDisable(false);

        enregistrerCarteButton.setVisible(true);
        modifierCarteButton.setDisable(true);
    }

    private void enregistrerCarte(Utilisateur u) {
        u.setTypeCarte(typeCarteField.getText());
        u.setNumeroCB(numeroCBField.getText());
        u.setCryptogramme(cryptoField.getText());
        u.setExpiration(expirationPicker.getValue());

        UtilisateurDAO.mettreAJourCarte(u);

        enregistrerCarteButton.setVisible(false);
        modifierCarteButton.setDisable(false);
        typeCarteField.setEditable(false);
        numeroCBField.setEditable(false);
        cryptoField.setEditable(false);
        expirationPicker.setDisable(true);
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
        u.setEmail(emailField.getText());
        u.setPassword(passwordField.getText());

        boolean success = UtilisateurDAO.mettreAJourUtilisateur(u);
        if (success) {
            emailField.setEditable(false);
            passwordField.setEditable(false);
            validerButton.setVisible(false);
            modifierButton.setDisable(false);
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
    public void onDeconnexion() {
        Session.deconnexion();
        SceneSwitcher.switchScene(
                (Stage) titreLabel.getScene().getWindow(),
                "/com/example/projet_java/accueil.fxml",
                "Accueil"
        );
    }

    private void afficherReductions(Utilisateur u) {
        if (reductionBox == null) return;

        reductionBox.getChildren().clear();

        var liste = com.example.JAVAdao.ReductionDAO.getReductionsParUtilisateur(u.getIdUser());
        if (liste.isEmpty()) {
            Label aucune = new Label("Aucune réduction disponible.");
            aucune.getStyleClass().add("text-dark");
            reductionBox.getChildren().add(aucune);
        } else {
            for (var red : liste) {
                Label label = new Label(  red.getCode() + " (" + red.getPourcentage() + "%)");
                label.getStyleClass().add("text-reduction");
                reductionBox.getChildren().add(label);
            }
        }
    }

}
