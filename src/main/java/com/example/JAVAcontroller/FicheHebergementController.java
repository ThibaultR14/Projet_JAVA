package com.example.JAVAcontroller;

import com.example.JAVAdao.HebergementDAO;
import com.example.JAVAdao.OptionHebergementDAO;
import com.example.JAVAdao.TarifDAO;
import com.example.JAVAmodel.Hebergement;
import com.example.JAVAmodel.OptionHebergement;
import com.example.JAVAmodel.Tarif;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException; // N'oublie pas d'importer IOException
import java.time.LocalDate;
import java.util.List;
import java.io.IOException;  // Nécessaire pour gérer l'IOException lors du chargement des FXML
import java.util.List;


public class FicheHebergementController {

    private Hebergement hebergement;

    @FXML private Label nomLabel;
    @FXML private Label adresseLabel;
    @FXML private Label etoilesLabel;
    @FXML private Label capaciteLabel;
    @FXML private Label tarifLabel;
    @FXML private Label datesLabel;
    @FXML private VBox optionsContainer;
    @FXML private ImageView photoView;

    public void setHebergement(Hebergement h) {
        this.hebergement = h;
        chargerInfos();
    }

    private void chargerInfos() {
        if (hebergement == null) return;

        // Infos de base
        nomLabel.setText(hebergement.getNom());
        adresseLabel.setText(hebergement.getAdresse());
        etoilesLabel.setText("★".repeat(hebergement.getNbEtoile()));
        capaciteLabel.setText("Capacité : " + hebergement.getCapaciteMin() + " à " + hebergement.getCapaciteMax() + " pers.");

        // Tarifs
        Tarif tarif = TarifDAO.getTarifById(hebergement.getIdTarif());
        if (tarif != null) {
            tarifLabel.setText("Adulte : " + tarif.getPrixAdulte() + "€, Enfant : " + tarif.getPrixEnfant() + "€, VIP : " + tarif.getPrixVIP() + "€");
        } else {
            tarifLabel.setText("Tarifs non disponibles");
        }

        // Dates d'ouverture
        String ouverture = hebergement.getDateOuverture() != null ? hebergement.getDateOuverture().toString() : "?";
        String fermeture = hebergement.getDateFermeture() != null ? hebergement.getDateFermeture().toString() : "?";
        datesLabel.setText("Ouvert du " + ouverture + " au " + fermeture);

        // Image
        try {
            Image image = new Image(getClass().getResource("/images/" + hebergement.getPhoto()).toExternalForm());
            photoView.setImage(image);
        } catch (Exception e) {
            photoView.setImage(new Image(getClass().getResource("/images/default.jpg").toExternalForm()));
        }

        // Options
        optionsContainer.getChildren().clear();
        List<OptionHebergement> options = OptionHebergementDAO.getOptionsByHebergement(hebergement.getIdHebergement());
        for (OptionHebergement opt : options) {
            Label lbl = new Label("- " + opt.getNom() + " (+ " + opt.getSupplement() + "€)");
            lbl.getStyleClass().add("text-dark");
            optionsContainer.getChildren().add(lbl);
        }
    }

    @FXML
    public void onRetourRecherche() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projet_java/recherche-hebergement.fxml"));

            // Charger la racine de la scène
            Parent root = loader.load();

            // Obtenir le contrôleur de la scène chargée
            RechercheHebergementController controller = loader.getController();
            controller.initData("", LocalDate.now(), LocalDate.now()); // Remplir avec des valeurs ou des données actuelles

            // Créer la nouvelle scène
            Scene scene = new Scene(root);

            // Appliquer le CSS à cette scène
            scene.getStylesheets().add(getClass().getResource("/com/example/projet_java/style.css").toExternalForm());

            // Mettre la scène dans le stage
            Stage stage = (Stage) nomLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Recherche d'Hébergements");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();  // Gérer l'exception si l'FXML n'est pas trouvé
        }
    }

}
