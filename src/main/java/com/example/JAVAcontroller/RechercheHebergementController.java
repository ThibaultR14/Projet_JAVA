package com.example.JAVAcontroller;

import com.example.JAVAdao.HebergementDAO;
import com.example.JAVAdao.TarifDAO;
import com.example.JAVAmodel.Tarif;
import com.example.JAVAmodel.Hebergement;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class RechercheHebergementController {

    @FXML private TextField villeField;
    @FXML private DatePicker dateArriveePicker;
    @FXML private DatePicker dateDepartPicker;
    @FXML private Spinner<Integer> nbAdultesSpinner;
    @FXML private Spinner<Integer> nbEnfantsSpinner;
    @FXML private VBox resultatsContainer;

    @FXML
    public void initialize() {
        nbAdultesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
        nbEnfantsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
    }

    public void initData(String ville, LocalDate dateArrivee, LocalDate dateDepart, int nbAdultes, int nbEnfants) {
        villeField.setText(ville);
        dateArriveePicker.setValue(dateArrivee);
        dateDepartPicker.setValue(dateDepart);

        nbAdultesSpinner.getValueFactory().setValue(nbAdultes);
        nbEnfantsSpinner.getValueFactory().setValue(nbEnfants);

        onRechercherClicked();
    }

    public void initData2(String ville, LocalDate dateArrivee, LocalDate dateDepart) {
        villeField.setText(ville);
        dateArriveePicker.setValue(dateArrivee);
        dateDepartPicker.setValue(dateDepart);


        onRechercherClicked();
    }



    @FXML
    public void onRechercherClicked() {
        String ville = villeField.getText();
        LocalDate dateArrivee = dateArriveePicker.getValue();
        LocalDate dateDepart = dateDepartPicker.getValue();
        int nbAdultes = nbAdultesSpinner.getValue();
        int nbEnfants = nbEnfantsSpinner.getValue();

        resultatsContainer.getChildren().clear();

        List<Hebergement> hebergements = HebergementDAO.findByCriteria(ville, nbAdultes, nbEnfants, dateArrivee, dateDepart);
        afficherResultats(hebergements, nbAdultes + nbEnfants);
    }

    private void afficherResultats(List<Hebergement> liste, int totalPersonnes) {
        int nbAdultes = nbAdultesSpinner.getValue();
        int nbEnfants = nbEnfantsSpinner.getValue();

        for (Hebergement h : liste) {
            if (totalPersonnes < h.getCapaciteMin() || totalPersonnes > h.getCapaciteMax()) continue;

            Tarif tarif = TarifDAO.getTarifById(h.getIdTarif());
            if (tarif == null) continue;

            int total = tarif.getPrixAdulte() * nbAdultes + tarif.getPrixEnfant() * nbEnfants;

            HBox carte = new HBox(15);
            carte.setStyle("-fx-padding: 15; -fx-background-color: #EFF6FF; -fx-border-color: #93C5FD; -fx-border-radius: 8;");
            carte.setAlignment(Pos.CENTER_LEFT);

            ImageView imageView = new ImageView();
            imageView.setFitHeight(100);
            imageView.setFitWidth(150);
            imageView.setPreserveRatio(true);

            try {
                String imagePath = "/images/" + (h.getPhoto() != null ? h.getPhoto() : "default.jpg");
                Image image = new Image(getClass().getResource(imagePath).toExternalForm());
                imageView.setImage(image);
            } catch (Exception e) {
                imageView.setImage(new Image(getClass().getResource("/images/default.jpg").toExternalForm()));
            }

            VBox texteBox = new VBox(8);
            Label nom = new Label(h.getNom());
            nom.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #1E293B;");

            Label adresse = new Label(h.getAdresse());
            adresse.setStyle("-fx-text-fill: #1E293B;");

            Label etoiles = new Label("★".repeat(h.getNbEtoile()));
            etoiles.setStyle("-fx-text-fill: gold;");

            texteBox.getChildren().addAll(nom, adresse, etoiles);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            VBox blocPrix = new VBox(5);
            blocPrix.setAlignment(Pos.CENTER_RIGHT);

            Label prix = new Label(total + " €");
            prix.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #1E293B;");

            Label details = new Label(nbAdultes + " adulte(s), " + nbEnfants + " enfant(s)");
            details.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B;");

            Button voirPlusBtn = new Button("Voir plus");
            voirPlusBtn.getStyleClass().add("button-secondary");
            voirPlusBtn.setOnAction(e -> ouvrirFicheHebergement(h));

            blocPrix.getChildren().addAll(prix, details, voirPlusBtn);
            carte.getChildren().addAll(imageView, texteBox, spacer, blocPrix);

            resultatsContainer.getChildren().add(carte);
        }

        if (resultatsContainer.getChildren().isEmpty()) {
            Label vide = new Label("Aucun hébergement ne correspond à votre recherche.");
            vide.getStyleClass().add("text-dark");
            resultatsContainer.getChildren().add(vide);
        }
    }

    public void setPersonnes(int adultes, int enfants) {
        nbAdultesSpinner.getValueFactory().setValue(adultes);
        nbEnfantsSpinner.getValueFactory().setValue(enfants);
    }


    private void ouvrirFicheHebergement(Hebergement h) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projet_java/fiche-hebergement.fxml"));
            Parent root = loader.load();

            FicheHebergementController controller = loader.getController();
            controller.setHebergement(h);

            controller.initRechercheContext(
                    villeField.getText(),
                    dateArriveePicker.getValue(),
                    dateDepartPicker.getValue(),
                    nbAdultesSpinner.getValue(),
                    nbEnfantsSpinner.getValue()
            );

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/example/projet_java/style.css").toExternalForm());

            Stage stage = (Stage) villeField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Détails de l'Hébergement");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
