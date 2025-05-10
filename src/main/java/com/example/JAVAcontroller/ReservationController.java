package com.example.JAVAcontroller;

import com.example.JAVAdao.ReservationDAO;
import com.example.JAVAdao.UtilisateurDAO;
import com.example.JAVAdao.HebergementDAO;

import com.example.JAVAmodel.Hebergement;
import com.example.JAVAmodel.Reservation;
import com.example.JAVAmodel.Utilisateur;
import com.example.projet_java.SceneSwitcher;
import com.example.projet_java.Session;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;



import java.time.LocalDate;

public class ReservationController {

    @FXML private Label hebergementLabel;
    @FXML private DatePicker dateArriveePicker;
    @FXML private DatePicker dateDepartPicker;
    @FXML private Spinner<Integer> nbAdultesSpinner;
    @FXML private Spinner<Integer> nbEnfantsSpinner;
    @FXML private Button reserverButton;
    @FXML private Label tarifEstimeLabel;
    @FXML private Label etoilesLabel;

    @FXML private TextField promoCodeField;
    private int pourcentageReduction = 0;


    @FXML private VBox carteFormBox;
    @FXML private TextField typeCarteField;
    @FXML private TextField numeroCBField;
    @FXML private TextField cryptoField;
    @FXML private DatePicker expirationPicker;
    @FXML private Button enregistrerCarteButton;




    @FXML private Label datesLabel;
    @FXML private Label capaciteLabel;
    @FXML private Label hebergementNomLabel;

    @FXML private VBox paiementBox;


    private Hebergement hebergement;

    private int prixAdulte = 0;
    private int prixEnfant = 0;

    public void setHebergement(Hebergement hebergement) {
        this.hebergement = hebergement;
        hebergementNomLabel.setText(hebergement.getNom());
        etoilesLabel.setText("★".repeat(hebergement.getNbEtoile()));



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
        afficherPaiement(); // <-- AJOUTER CETTE LIGNE ICI

    }

    private void calculerPrix() {
        int nbA = nbAdultesSpinner.getValue();
        int nbE = nbEnfantsSpinner.getValue();
        int total = nbA * prixAdulte + nbE * prixEnfant;

        if (pourcentageReduction > 0) {
            total -= total * pourcentageReduction / 100;
        }

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
            HebergementDAO.marquerCommeReserve(hebergement.getIdHebergement());

            showConfirmation("Réservation enregistrée !");
            SceneSwitcher.switchScene(
                    (Stage) dateArriveePicker.getScene().getWindow(),
                    "/com/example/projet_java/mes-reservations.fxml",
                    "Mes réservations"
            );
        } else {
            showError("Erreur lors de l'enregistrement.");
        }
    }

    @FXML
    public void onRetourFicheHebergementClicked() {
        if (hebergement != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projet_java/fiche-hebergement.fxml"));
                Parent root = loader.load();

                FicheHebergementController controller = loader.getController();
                controller.setHebergement(hebergement);

                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/com/example/projet_java/style.css").toExternalForm());

                Stage stage = (Stage) reserverButton.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Détails de l'Hébergement");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void afficherPaiement() {
        paiementBox.getChildren().clear();

        var utilisateur = Session.getUtilisateur();
        if (utilisateur == null) return;

        // Masquer proprement le formulaire de carte
        carteFormBox.setVisible(false);
        carteFormBox.setManaged(false);
        if (utilisateur.getNumeroCB() == null || utilisateur.getNumeroCB().isBlank()) {
            Label label = new Label("Aucune carte enregistrée.");
            Button allerCompteBtn = new Button("Ajouter une carte bancaire");
            allerCompteBtn.getStyleClass().add("button-primary");
            allerCompteBtn.setOnAction(e -> activerEditionCarte(true));
            paiementBox.getChildren().addAll(label, allerCompteBtn);
        } else {
            GridPane carteInfos = new GridPane();
            carteInfos.setHgap(10);
            carteInfos.setVgap(8);

            String numMasque = "**** **** **** " + utilisateur.getNumeroCB().substring(utilisateur.getNumeroCB().length() - 4);
            String cryptoMasque = "***";

            TextField typeCarteFieldView = new TextField(utilisateur.getTypeCarte());
            TextField numeroCBFieldView = new TextField(numMasque);
            TextField cryptoFieldView = new TextField(cryptoMasque);
            DatePicker expirationPickerView = new DatePicker(utilisateur.getExpiration());

            typeCarteFieldView.setEditable(false);
            numeroCBFieldView.setEditable(false);
            cryptoFieldView.setEditable(false);
            expirationPickerView.setDisable(true);

            carteInfos.addRow(0, new Label("Type de carte :"), typeCarteFieldView);
            carteInfos.addRow(1, new Label("Numéro :"), numeroCBFieldView);
            carteInfos.addRow(2, new Label("Cryptogramme :"), cryptoFieldView);
            carteInfos.addRow(3, new Label("Expiration :"), expirationPickerView);

            Button modifierCarteButton = new Button("Modifier la carte");
            modifierCarteButton.getStyleClass().add("button-secondary");
            modifierCarteButton.setOnAction(e -> activerEditionCarte(false));

            Button supprimerCarteButton = new Button("Supprimer la carte");
            supprimerCarteButton.getStyleClass().add("button-logout");
            supprimerCarteButton.setOnAction(e -> {
                utilisateur.setNumeroCB(null);
                utilisateur.setCryptogramme(null);
                utilisateur.setExpiration(null);
                utilisateur.setTypeCarte(null);
                UtilisateurDAO.mettreAJourCarte(utilisateur);
                afficherPaiement();
            });

            paiementBox.getChildren().addAll(carteInfos, modifierCarteButton, supprimerCarteButton);
        }
    }


    private void activerEditionCarte(boolean isAjout) {
        // Pré-remplir ou vider selon le contexte
        var utilisateur = Session.getUtilisateur();
        if (utilisateur == null) return;

        if (!isAjout) {
            typeCarteField.setText(utilisateur.getTypeCarte());
            numeroCBField.setText(utilisateur.getNumeroCB());
            cryptoField.setText(utilisateur.getCryptogramme());
            expirationPicker.setValue(utilisateur.getExpiration());
        } else {
            typeCarteField.clear();
            numeroCBField.clear();
            cryptoField.clear();
            expirationPicker.setValue(null);
        }

        // Le formulaire vient du FXML : on le met dans paiementBox
        paiementBox.getChildren().setAll(carteFormBox);
        carteFormBox.setVisible(true);
        carteFormBox.setManaged(true);
    }


    @FXML
    private void enregistrerCarte() {
        Utilisateur u = Session.getUtilisateur();
        if (u == null) return;

        String typeCarte = typeCarteField.getText();
        String numeroCB = numeroCBField.getText();
        String cryptogramme = cryptoField.getText();
        LocalDate expiration = expirationPicker.getValue();

        if (typeCarte.isEmpty() || numeroCB.isEmpty() || cryptogramme.isEmpty() || expiration == null) {
            showError("Veuillez remplir tous les champs.");
            return;
        }

        u.setTypeCarte(typeCarte);
        u.setNumeroCB(numeroCB);
        u.setCryptogramme(cryptogramme);
        u.setExpiration(expiration);

        UtilisateurDAO.mettreAJourCarte(u);

        showConfirmation("Carte enregistrée !");
        afficherPaiement();  // recharge dans paiementBox
    }



    @FXML
    public void onEnregistrerCarteClicked() {
        var utilisateur = Session.getUtilisateur();
        if (utilisateur == null) return;

        utilisateur.setTypeCarte(typeCarteField.getText());
        utilisateur.setNumeroCB(numeroCBField.getText());
        utilisateur.setCryptogramme(cryptoField.getText());
        utilisateur.setExpiration(expirationPicker.getValue());

        UtilisateurDAO.mettreAJourCarte(utilisateur); // On n'attend plus de résultat

        showConfirmation("Carte enregistrée !");
        afficherPaiement();  // Recharge les infos
    }

    @FXML
    public void onAppliquerPromoClicked() {
        String code = promoCodeField.getText();
        if (code == null || code.isBlank()) return;

        var utilisateur = Session.getUtilisateur();
        var reduction = com.example.JAVAdao.ReductionDAO.getReductionByCodeAndUser(code.trim(), utilisateur.getIdUser());

        if (reduction == null) {
            showError("Code invalide ou non attribué à votre compte.");
            pourcentageReduction = 0;
        } else {
            pourcentageReduction = reduction.getPourcentage();
            showConfirmation("Code appliqué : -" + pourcentageReduction + "%");
        }

        calculerPrix();
    }








}
