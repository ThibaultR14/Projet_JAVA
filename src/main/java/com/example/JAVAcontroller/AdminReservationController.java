package com.example.JAVAcontroller;

import com.example.JAVAdao.AdminReservationDAO;
import com.example.JAVAmodel.AdminReservation;
import com.example.projet_java.SceneSwitcher;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminReservationController {

    @FXML private TableView<AdminReservation> reservationTable;
    @FXML private TableColumn<AdminReservation, Integer> idCol;
    @FXML private TableColumn<AdminReservation, String> userCol;
    @FXML private TableColumn<AdminReservation, String> hebergementCol;
    @FXML private TableColumn<AdminReservation, String> dateArriveeCol;
    @FXML private TableColumn<AdminReservation, String> dateDepartCol;
    @FXML private TableColumn<AdminReservation, Integer> adulteCol;
    @FXML private TableColumn<AdminReservation, Integer> enfantCol;

    @FXML public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("idReservation"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("nomUser"));
        hebergementCol.setCellValueFactory(new PropertyValueFactory<>("nomHebergement"));
        dateArriveeCol.setCellValueFactory(new PropertyValueFactory<>("dateArrivee"));
        dateDepartCol.setCellValueFactory(new PropertyValueFactory<>("dateDepart"));
        adulteCol.setCellValueFactory(new PropertyValueFactory<>("nbAdulte"));
        enfantCol.setCellValueFactory(new PropertyValueFactory<>("nbEnfant"));

        reservationTable.setItems(FXCollections.observableArrayList(AdminReservationDAO.getAllReservations()));
    }

    @FXML
    public void supprimerReservation() {
        AdminReservation selected = reservationTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            AdminReservationDAO.deleteReservation(selected.getIdReservation());
            reservationTable.getItems().remove(selected);
        }
    }


    @FXML
    public void retourAccueil() {
        Stage stage = (Stage) reservationTable.getScene().getWindow();
        SceneSwitcher.switchScene(stage, "/com/example/projet_java/adminbienvenue.fxml", "Accueil Admin");
    }
    @FXML
    private void modifierReservation() {
        AdminReservation selected = reservationTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projet_java/modifierreservation.fxml"));
                Parent root = loader.load();

                AdminModifierReservationController controller = loader.getController();
                controller.setReservation(selected);

                Stage stage = new Stage();
                stage.setTitle("Modifier la réservation");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Recharger les données après modification
                reservationTable.setItems(FXCollections.observableArrayList(AdminReservationDAO.getAllReservations()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner une réservation.");
            alert.showAndWait();
        }
    }
}
