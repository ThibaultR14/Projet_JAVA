package com.example.JAVAcontroller;

import com.example.JAVAdao.HebergementDAO;
import com.example.JAVAmodel.Hebergement;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class RechercheHebergementController {

    @FXML
    private TextField villeField;

    @FXML
    private DatePicker dateArriveePicker;

    @FXML
    private DatePicker dateDepartPicker;

    @FXML
    private TextField nbAdultesField;

    @FXML
    private TextField nbEnfantsField;

    @FXML
    private TableView<Hebergement> hebergementTable;

    @FXML
    private TableColumn<Hebergement, String> nomColumn;

    @FXML
    private TableColumn<Hebergement, String> adresseColumn;

    @FXML
    private TableColumn<Hebergement, Integer> etoileColumn;

    @FXML
    private TableColumn<Hebergement, Integer> villeColumn;

    private HebergementDAO hebergementDAO;

    // Attributs pour stocker les données passées
    private String ville;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;

    @FXML
    public void initialize() {
        hebergementDAO = new HebergementDAO();

        nomColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNom()));
        adresseColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAdresse()));
        etoileColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getNbEtoile()));
        villeColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getIdVille()));
    }

    // Méthode pour recevoir les données de la page d'accueil
    public void initData(String ville, LocalDate dateArrivee, LocalDate dateDepart) {
        this.ville = ville;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;

        // Affichage des données pour vérifier
        System.out.println("Données reçues dans RechercheHebergementController :");
        System.out.println("Ville = " + ville);
        System.out.println("Date arrivée = " + dateArrivee);
        System.out.println("Date départ = " + dateDepart);

        // Optionnel : Tu peux aussi remplir les champs de la page avec ces valeurs
        villeField.setText(ville);
        dateArriveePicker.setValue(dateArrivee);
        dateDepartPicker.setValue(dateDepart);
    }

    @FXML
    public void onRechercherClicked() {
        String ville = villeField.getText();
        LocalDate dateArrivee = dateArriveePicker.getValue();
        LocalDate dateDepart = dateDepartPicker.getValue();
        int nbAdultes;
        int nbEnfants;

        try {
            nbAdultes = Integer.parseInt(nbAdultesField.getText());
            nbEnfants = Integer.parseInt(nbEnfantsField.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des nombres valides pour adultes et enfants !");
            return;
        }

        if (dateArrivee == null || dateDepart == null || dateArrivee.isAfter(dateDepart)) {
            showAlert("Erreur", "Veuillez sélectionner une date d'arrivée et de départ valide !");
            return;
        }

        // ⚡ Recherche basée sur la capacité et la disponibilité
        List<Hebergement> hebergements = hebergementDAO.rechercherParCriteres(ville, dateArrivee, dateDepart, nbAdultes, nbEnfants);
        ObservableList<Hebergement> observableList = FXCollections.observableArrayList(hebergements);
        hebergementTable.setItems(observableList);
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
