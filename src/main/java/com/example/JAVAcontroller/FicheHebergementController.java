package com.example.JAVAcontroller;

import com.example.projet_java.SceneSwitcher;
import com.example.projet_java.Session;
import com.example.JAVAdao.OptionHebergementDAO;
import com.example.JAVAdao.TarifDAO;
import com.example.JAVAdao.VilleDAO;
import com.example.JAVAmodel.Hebergement;
import com.example.JAVAmodel.OptionHebergement;
import com.example.JAVAmodel.Tarif;
import com.example.JAVAmodel.Ville;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FicheHebergementController {

    private Hebergement hebergement;
    private String villeRecherche;
    private LocalDate dateArriveeRecherche;
    private LocalDate dateDepartRecherche;
    private int nbAdultesRecherche;
    private int nbEnfantsRecherche;

    @FXML private Label nomLabel;
    @FXML private Label adresseLabel;
    @FXML private Label etoilesLabel;
    @FXML private Label capaciteLabel;
    @FXML private Label tarifLabel;
    @FXML private Label datesLabel;
    @FXML private Label villeLabel;
    @FXML private VBox optionsContainer;
    @FXML private ImageView photoView;

    public void setHebergement(Hebergement h) {
        this.hebergement = h;
        chargerInfos();
    }

    public void initRechercheContext(String ville, LocalDate dateArrivee, LocalDate dateDepart, int nbAdultes, int nbEnfants) {
        this.villeRecherche = ville;
        this.dateArriveeRecherche = dateArrivee;
        this.dateDepartRecherche = dateDepart;
        this.nbAdultesRecherche = nbAdultes;
        this.nbEnfantsRecherche = nbEnfants;
    }

    private void chargerInfos() {
        if (hebergement == null) return;

        nomLabel.setText(hebergement.getNom());
        adresseLabel.setText(hebergement.getAdresse());
        etoilesLabel.setText("★".repeat(hebergement.getNbEtoile()));
        capaciteLabel.setText("Capacité : " + hebergement.getCapaciteMin() + " à " + hebergement.getCapaciteMax() + " pers.");

        // Ville
        Ville ville = VilleDAO.getVilleById(hebergement.getIdVille());
        if (ville != null) {
            villeLabel.setText("Ville : " + ville.getNom() + " (" + ville.getCodePostal() + ")");
        } else {
            villeLabel.setText("Ville inconnue");
        }

        // Tarifs
        Tarif tarif = TarifDAO.getTarifById(hebergement.getIdTarif());
        if (tarif != null) {
            tarifLabel.setText("Adulte : " + tarif.getPrixAdulte() + "€, Enfant : " + tarif.getPrixEnfant() + "€, VIP : " + tarif.getPrixVIP() + "€");
        } else {
            tarifLabel.setText("Tarifs non disponibles");
        }

        // Dates
        String ouverture = hebergement.getDateOuverture() != null ? hebergement.getDateOuverture().toString() : "?";
        String fermeture = hebergement.getDateFermeture() != null ? hebergement.getDateFermeture().toString() : "?";
        datesLabel.setText("Ouvert du " + ouverture + " au " + fermeture);

        // Image
        Image image = null;
        String imagePath = "/images/" + hebergement.getPhoto();

        try {
            image = new Image(getClass().getResource(imagePath).toExternalForm());
        } catch (Exception e) {
            // Fallback system file
            File file = new File("src/main/resources/images/" + hebergement.getPhoto());
            if (file.exists()) {
                image = new Image(file.toURI().toString());
            } else {
                image = new Image(getClass().getResource("/images/default.jpg").toExternalForm());
            }
        }
        photoView.setImage(image);

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
            Parent root = loader.load();

            RechercheHebergementController controller = loader.getController();
            controller.initData(villeRecherche, dateArriveeRecherche, dateDepartRecherche, nbAdultesRecherche, nbEnfantsRecherche);
            controller.setPersonnes(nbAdultesRecherche, nbEnfantsRecherche);

            Stage stage = (Stage) nomLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add(getClass().getResource("/com/example/projet_java/style.css").toExternalForm());
            stage.setTitle("Recherche d'Hébergements");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onReserverClicked() {
        if (Session.getUtilisateur() == null) {
            SceneSwitcher.switchScene(
                    (Stage) nomLabel.getScene().getWindow(),
                    "/com/example/projet_java/connexion.fxml",
                    "Connexion"
            );
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projet_java/reservation.fxml"));
            Parent root = loader.load();

            ReservationController controller = loader.getController();
            controller.setHebergement(hebergement);

            Stage stage = (Stage) nomLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.getScene().getStylesheets().add(getClass().getResource("/com/example/projet_java/style.css").toExternalForm());
            stage.setTitle("Réserver l'Hébergement");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
