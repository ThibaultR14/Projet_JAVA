package com.example.JAVAcontroller;

import com.example.JAVAdao.ReservationDAO;
import com.example.JAVAmodel.Hebergement;
import com.example.JAVAmodel.Reservation;
import com.example.projet_java.SceneSwitcher;
import com.example.projet_java.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ReservationController {

    @FXML private Label hebergementLabel;
    @FXML private DatePicker dateArriveePicker;
    @FXML private DatePicker dateDepartPicker;
    @FXML private Spinner<Integer> nbAdultesSpinner;
    @FXML private Spinner<Integer> nbEnfantsSpinner;
    @FXML private Button reserverButton;
    @FXML private Label tarifEstimeLabel;



    @FXML private Label datesLabel;
    @FXML private Label capaciteLabel;
    @FXML private Label hebergementNomLabel;

    private Hebergement hebergement;

    private int prixAdulte = 0;
    private int prixEnfant = 0;

    public void setHebergement(Hebergement hebergement) {
        this.hebergement = hebergement;
        hebergementNomLabel.setText(hebergement.getNom());

        // ⚠️ Charger le tarif
        var tarif = com.example.JAVAdao.TarifDAO.getTarifById(hebergement.getIdTarif());
        if (tarif != null) {
            prixAdulte = tarif.getPrixAdulte();
            prixEnfant = tarif.getPrixEnfant();
        }

        // 🔒 Bloquer les dates invalides
        if (hebergement.getDateOuverture() != null && hebergement.getDateFermeture() != null) {
            dateArriveePicker.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(empty || date.isBefore(hebergement.getDateOuverture()) || date.isAfter(hebergement.getDateFermeture()));
                }
            });

            dateDepartPicker.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(empty || date.isBefore(hebergement.getDateOuverture()) || date.isAfter(hebergement.getDateFermeture()));
                }
            });

            // ➕ Afficher les dates autorisées
            datesLabel.setText("(Ouvert du " + hebergement.getDateOuverture() + " au " + hebergement.getDateFermeture() + ")");
        }

        // 🔢 Configurer les spinners
        int min = hebergement.getCapaciteMin();
        int max = hebergement.getCapaciteMax();

        nbAdultesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, max, Math.min(min, max)));
        nbEnfantsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, max - 1, 0));

        // 💬 Affichage capacité
        capaciteLabel.setText("(Capacité : " + min + " à " + max + ")");

        // 🔁 Réactions dynamiques
        nbAdultesSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            int reste = max - newVal;
            int currentEnfant = nbEnfantsSpinner.getValue();
            nbEnfantsSpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, reste, Math.min(currentEnfant, reste))
            );
            calculerPrix();
        });

        nbEnfantsSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
            int reste = max - newVal;
            int currentAdulte = nbAdultesSpinner.getValue();
            nbAdultesSpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, reste, Math.min(currentAdulte, reste))
            );
            calculerPrix();
        });

        calculerPrix();
    }

    private void calculerPrix() {
        int nbA = nbAdultesSpinner.getValue();
        int nbE = nbEnfantsSpinner.getValue();
        int total = nbA * prixAdulte + nbE * prixEnfant;

        tarifEstimeLabel.setText("Tarif estimé : " + total + "€");
    }


    private void ajusterEnfants() {
        int nbAdultes = nbAdultesSpinner.getValue();
        int capaciteMax = hebergement.getCapaciteMax();
        int capaciteMin = hebergement.getCapaciteMin();

        int minEnfants = Math.max(0, capaciteMin - nbAdultes);
        int maxEnfants = Math.max(0, capaciteMax - nbAdultes);

        nbEnfantsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                minEnfants, maxEnfants, Math.max(minEnfants, Math.min(nbEnfantsSpinner.getValue(), maxEnfants))
        ));
    }

    private void ajusterAdultes() {
        int nbEnfants = nbEnfantsSpinner.getValue();
        int capaciteMax = hebergement.getCapaciteMax();
        int capaciteMin = hebergement.getCapaciteMin();

        int minAdultes = Math.max(1, capaciteMin - nbEnfants);
        int maxAdultes = Math.max(1, capaciteMax - nbEnfants);

        nbAdultesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                minAdultes, maxAdultes, Math.max(minAdultes, Math.min(nbAdultesSpinner.getValue(), maxAdultes))
        ));
    }

    @FXML
    public void initialize() {
        // Valeurs par défaut, seront écrasées dans setHebergement
        nbAdultesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
        nbEnfantsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0));
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void onReserverClicked() {
        if (hebergement == null || Session.getUtilisateur() == null) return;

        LocalDate arrivee = dateArriveePicker.getValue();
        LocalDate depart = dateDepartPicker.getValue();
        int nbAdulte = nbAdultesSpinner.getValue();
        int nbEnfant = nbEnfantsSpinner.getValue();
        int totalPers = nbAdulte + nbEnfant;

        // ✅ Vérification des dates sélectionnées
        if (arrivee == null || depart == null || arrivee.isAfter(depart)) {
            showError("Veuillez sélectionner des dates valides.");
            return;
        }

        // ✅ Vérification contre les dates d'ouverture de l'hébergement
        if ((hebergement.getDateOuverture() != null && arrivee.isBefore(hebergement.getDateOuverture())) ||
                (hebergement.getDateFermeture() != null && depart.isAfter(hebergement.getDateFermeture()))) {
            showError("Les dates choisies ne correspondent pas à la période d'ouverture de l'hébergement.");
            return;
        }

        // ✅ Vérification de la capacité minimale
        if (totalPers < hebergement.getCapaciteMin()) {
            showError("Le nombre total de personnes est inférieur à la capacité minimale requise (" + hebergement.getCapaciteMin() + ").");
            return;
        }

        // ✅ Vérification de la capacité maximale
        if (totalPers > hebergement.getCapaciteMax()) {
            showError("Le nombre de personnes dépasse la capacité maximale autorisée (" + hebergement.getCapaciteMax() + ").");
            return;
        }

        // ✅ Création de la réservation
        Reservation r = new Reservation(
                arrivee,
                depart,
                nbAdulte,
                nbEnfant,
                Session.getUtilisateur().getIdUser(),
                hebergement.getIdHebergement()
        );

        boolean ok = ReservationDAO.ajouterReservation(r);

        if (ok) {
            showConfirmation("Réservation enregistrée !");
            SceneSwitcher.switchScene(
                    (Stage) dateArriveePicker.getScene().getWindow(),
                    "/com/example/projet_java/mon-compte.fxml",
                    "Mon Compte"
            );
        } else {
            showError("Erreur lors de l'enregistrement.");
        }
    }

}
