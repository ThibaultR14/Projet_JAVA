package com.example.JAVAcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.example.JAVAdao.HebergementDAO;
import com.example.JAVAdao.TarifDAO;
import com.example.JAVAmodel.Hebergement;
import com.example.JAVAmodel.Tarif;
import com.example.projet_java.Session;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class AjoutHebergementController {

    // Champs hébergement
    @FXML private TextField nomField;
    @FXML private TextField adresseField;
    @FXML private TextField capaciteMinField;
    @FXML private TextField capaciteMaxField;
    @FXML private TextField etoilesField;
    @FXML private TextField villeField;
    @FXML private TextField codePostalField;
    @FXML private DatePicker dateOuverturePicker;
    @FXML private DatePicker dateFermeturePicker;

    // Tarifs
    @FXML private TextField prixAdulteField;
    @FXML private TextField prixEnfantField;
    @FXML private TextField prixVIPField;

    // Drag & Drop image
    @FXML private VBox dropZone;
    @FXML private Label dropLabel;
    @FXML private ImageView previewImage;

    private File selectedImageFile;

    @FXML
    public void initialize() {
        dropZone.setOnDragOver(event -> {
            if (event.getGestureSource() != dropZone && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        dropZone.setOnDragDropped(event -> {
            var db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                selectedImageFile = db.getFiles().get(0);
                dropLabel.setText("Image : " + selectedImageFile.getName());
                previewImage.setImage(new Image(selectedImageFile.toURI().toString()));
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }

    @FXML
    public void onSubmit() {
        try {
            // Lecture des champs de formulaire
            String nom = nomField.getText();
            String adresse = adresseField.getText();
            int capaciteMin = Integer.parseInt(capaciteMinField.getText());
            int capaciteMax = Integer.parseInt(capaciteMaxField.getText());
            int nbEtoile = Integer.parseInt(etoilesField.getText());
            String ville = villeField.getText(); // non utilisé ici
            String codePostal = codePostalField.getText(); // non utilisé ici
            LocalDate dateOuverture = dateOuverturePicker.getValue();
            LocalDate dateFermeture = dateFermeturePicker.getValue();

            // Tarifs
            int prixAdulte = Integer.parseInt(prixAdulteField.getText());
            int prixEnfant = Integer.parseInt(prixEnfantField.getText());
            int prixVIP = Integer.parseInt(prixVIPField.getText());

            Tarif tarif = new Tarif(prixAdulte, prixEnfant, prixVIP);
            int idTarif = TarifDAO.ajouterEtRetournerId(tarif);

            // Image
            String cheminFinal = "";
            if (selectedImageFile != null) {
                File destination = new File("src/main/resources/images/" + selectedImageFile.getName());
                copyFile(selectedImageFile, destination);
                cheminFinal = selectedImageFile.getName();
            }

            // Ville en dur (à améliorer dans le futur)
            int idVille = 1;

            // Récupération de l'utilisateur connecté
            int idUser = Session.getUtilisateur().getIdUser();

            // Création de l'hébergement
            Hebergement h = new Hebergement(
                    nom, adresse, nbEtoile,
                    capaciteMin, capaciteMax,
                    cheminFinal, idTarif, idVille,
                    dateOuverture, dateFermeture
            );
            h.setIdUser(idUser); // ✅ Lier à l'utilisateur connecté

            new HebergementDAO().ajouterHebergement(h);

            showConfirmation("Hébergement publié avec succès !");
            clearFields();

            // Redirection
            Stage stage = (Stage) nomField.getScene().getWindow();
            com.example.projet_java.SceneSwitcher.switchScene(stage, "/com/example/projet_java/accueil.fxml", "Accueil");

        } catch (Exception e) {
            showError("Erreur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void copyFile(File source, File destination) throws IOException {
        try (FileInputStream in = new FileInputStream(source);
             FileOutputStream out = new FileOutputStream(destination)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }

    private void showConfirmation(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void clearFields() {
        nomField.clear();
        adresseField.clear();
        capaciteMinField.clear();
        capaciteMaxField.clear();
        etoilesField.clear();
        villeField.clear();
        codePostalField.clear();
        dateOuverturePicker.setValue(null);
        dateFermeturePicker.setValue(null);
        prixAdulteField.clear();
        prixEnfantField.clear();
        prixVIPField.clear();
        dropLabel.setText("Déposez une image ici");
        previewImage.setImage(null);
        selectedImageFile = null;
    }
}
