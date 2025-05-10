package com.example.JAVAcontroller;

import com.example.JAVAdao.HebergementDAO;
import com.example.JAVAmodel.Hebergement;
import com.example.projet_java.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class MesHebergementsController {

    @FXML
    private VBox hebergementsContainer;

    @FXML
    public void initialize() {
        rafraichirListe();
    }

    private void rafraichirListe() {
        hebergementsContainer.getChildren().clear();

        if (!Session.estConnecte()) {
            hebergementsContainer.getChildren().add(new Label("Veuillez vous connecter."));
            return;
        }

        int idUser = Session.getUtilisateur().getIdUser();
        List<Hebergement> hebergements = HebergementDAO.getHebergementsByUserId(idUser);

        if (hebergements.isEmpty()) {
            Label vide = new Label("Vous n'avez publié aucun hébergement.");
            vide.getStyleClass().add("text-dark");
            hebergementsContainer.getChildren().add(vide);
            return;
        }

        for (Hebergement h : hebergements) {
            HBox ligne = new HBox(20);
            ligne.setSpacing(20);
            ligne.setStyle(
                    "-fx-padding: 10;" +
                            "-fx-background-color: " + (h.isReserve() ? "#D1FAE5" : "#F1F5F9") + ";" + // vert clair ou gris clair
                            "-fx-border-color: #CBD5E1;" +
                            "-fx-border-radius: 8;" +
                            "-fx-background-radius: 8;"
            );
            ligne.getStyleClass().add("card");

            Label nom = new Label(h.getNom());
            nom.getStyleClass().add("hebergement-nom");

            Label capacite = new Label("Capacité : " + h.getCapaciteMin() + " - " + h.getCapaciteMax());
            Label periode = new Label("Disponible du " + h.getDateOuverture() + " au " + h.getDateFermeture());

            Label etat = new Label(h.isReserve() ? "🔒 Réservé" : "✅ Disponible");
            etat.setStyle("-fx-font-weight: bold; -fx-text-fill: " + (h.isReserve() ? "#065F46" : "#1E40AF") + ";");

            Button supprimerBtn = new Button("Supprimer");
            supprimerBtn.getStyleClass().add("button-logout");
            supprimerBtn.setOnAction(e -> {
                boolean ok = HebergementDAO.delete(h.getIdHebergement());
                if (ok) {
                    showConfirmation("Hébergement supprimé.");
                    rafraichirListe();
                } else {
                    showErreur("Erreur lors de la suppression.");
                }
            });

            VBox infos = new VBox(5, nom, capacite, periode, etat);
            ligne.getChildren().addAll(infos, supprimerBtn);
            HBox.setHgrow(infos, Priority.ALWAYS);

            hebergementsContainer.getChildren().add(ligne);
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
