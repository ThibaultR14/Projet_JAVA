package com.example.JAVAcontroller;

import com.example.JAVAdao.HebergementDAO;
import com.example.projet_java.SceneSwitcher;
import com.example.projet_java.Session;
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
import java.io.File;


import java.io.IOException; // N'oublie pas d'importer IOException
import java.time.LocalDate;
import java.util.List;
import java.io.IOException;  // Nécessaire pour gérer l'IOException lors du chargement des FXML
import java.util.List;


public class FicheHebergementController {

    private Hebergement hebergement;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;
    private int nbAdultes;
    private int nbEnfants;


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

    private String villeRecherche;
    private LocalDate dateArriveeRecherche;
    private LocalDate dateDepartRecherche;
    private int nbAdultesRecherche;
    private int nbEnfantsRecherche;

    public void initRechercheContext(String ville, LocalDate dateArrivee, LocalDate dateDepart, int nbAdultes, int nbEnfants) {
        this.villeRecherche = ville;
        this.dateArriveeRecherche = dateArrivee;
        this.dateDepartRecherche = dateDepart;
        this.nbAdultesRecherche = nbAdultes;
        this.nbEnfantsRecherche = nbEnfants;
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

        // ✅ Chargement de l'image
        Image image = null;
        String imagePath = "/images/" + hebergement.getPhoto();

        try {
            // 1. Essayer via classpath
            image = new Image(getClass().getResource(imagePath).toExternalForm());
        } catch (Exception e) {
            System.out.println("Image non trouvée dans classpath, tentative via File...");
        }

        if (image == null || image.isError()) {
            // 2. Fallback : accès direct depuis le système de fichiers
            File file = new File("C:/Users/PC/IdeaProjects/Projet_JAVA/src/main/resources/images/" + hebergement.getPhoto());
            if (file.exists()) {
                System.out.println("Yeahh");
                image = new Image(file.toURI().toString());
            } else {
                System.out.println("No");

                // 3. Si rien ne marche : fallback sur image par défaut
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

            // ✅ Réinjecte les anciens critères
            controller.initData(villeRecherche, dateArriveeRecherche, dateDepartRecherche, nbAdultesRecherche, nbEnfantsRecherche);
            controller.setPersonnes(nbAdultesRecherche, nbEnfantsRecherche);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/example/projet_java/style.css").toExternalForm());

            Stage stage = (Stage) nomLabel.getScene().getWindow();
            stage.setScene(scene);
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

            // Transmettre l’hébergement au controller
            ReservationController controller = loader.getController();
            controller.setHebergement(hebergement); // ⬅️ clé du problème

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/example/projet_java/style.css").toExternalForm());

            Stage stage = (Stage) nomLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Réserver l'Hébergement");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
