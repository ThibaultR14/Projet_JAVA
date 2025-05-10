package com.example.JAVAcontroller;

import com.example.JAVAdao.ReservationDAO;
import com.example.JAVAdao.HebergementDAO;
import com.example.JAVAmodel.Reservation;
import com.example.JAVAmodel.Hebergement;
import com.example.projet_java.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.List;

public class MesReservationsController {

    @FXML
    private VBox reservationsContainer;

    @FXML
    public void initialize() {
        rafraichirReservations();
    }

    private void rafraichirReservations() {
        reservationsContainer.getChildren().clear();

        if (!Session.estConnecte()) {
            Label msg = new Label("Veuillez vous connecter pour consulter vos réservations.");
            msg.getStyleClass().add("text-dark");
            reservationsContainer.getChildren().add(msg);
            return;
        }

        int idUser = Session.getUtilisateur().getIdUser();
        List<Reservation> reservations = ReservationDAO.getReservationsByUserId(idUser);

        if (reservations.isEmpty()) {
            Label vide = new Label("Vous n'avez encore aucune réservation.");
            vide.getStyleClass().add("text-dark");
            reservationsContainer.getChildren().add(vide);
            return;
        }

        for (Reservation r : reservations) {
            HBox ligne = new HBox(20);
            ligne.setStyle("-fx-padding: 10; -fx-background-color: #F1F5F9; -fx-border-color: #CBD5E1;");
            ligne.getStyleClass().add("card");

            Hebergement h = HebergementDAO.getHebergementById(r.getIdHebergement());
            String nomHebergement = (h != null) ? h.getNom() : "Inconnu";

            Label destination = new Label(nomHebergement);
            destination.getStyleClass().add("hebergement-nom");
            Label dates = new Label("Du " + r.getDateArrivee() + " au " + r.getDateDepart());
            Label personnes = new Label(r.getNbAdulte() + " adulte(s), " + r.getNbEnfant() + " enfant(s)");

            // 🔴 Bouton Annuler
            Button annulerBtn = new Button("Annuler");
            annulerBtn.getStyleClass().add("button-logout");
            annulerBtn.setOnAction(e -> {
                boolean deleted = ReservationDAO.supprimerReservation(r.getIdHebergement(), r.getIdUser());
                if (deleted) {
                    HebergementDAO.marquerCommeNonReserve(r.getIdHebergement());
                    showConfirmation("Réservation annulée.");
                    rafraichirReservations();
                } else {
                    showErreur("Erreur lors de l'annulation.");
                }
            });

            ligne.getChildren().addAll(destination, dates, personnes, annulerBtn);
            HBox.setHgrow(destination, Priority.ALWAYS);

            reservationsContainer.getChildren().add(ligne);
        }
    }

    private void showConfirmation(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showErreur(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
