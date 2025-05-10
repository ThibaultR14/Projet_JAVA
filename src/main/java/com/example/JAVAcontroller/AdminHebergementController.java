package com.example.JAVAcontroller;

import com.example.JAVAdao.AdminHebergementDAO;
import com.example.JAVAmodel.AdminHebergement;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminHebergementController {

    @FXML private TableView<AdminHebergement> hebergementTable;
    @FXML private TableColumn<AdminHebergement, String> nomCol;
    @FXML private TableColumn<AdminHebergement, String> adresseCol;
    @FXML private TableColumn<AdminHebergement, Integer> etoilesCol;

    @FXML private Button modifierBtn;
    @FXML private Button supprimerBtn;

    @FXML
    public void initialize() {
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        etoilesCol.setCellValueFactory(new PropertyValueFactory<>("nbEtoile"));

        hebergementTable.setItems(FXCollections.observableArrayList(AdminHebergementDAO.getAllHebergements()));
    }

    @FXML
    public void supprimerHebergement() {
        AdminHebergement selected = hebergementTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            AdminHebergementDAO.deleteHebergement(selected.getIdHebergement());
            hebergementTable.getItems().remove(selected);
        } else {
            showAlert("Veuillez sélectionner un hébergement à supprimer.");
        }
    }

    @FXML
    public void modifierHebergement() {
        AdminHebergement selected = hebergementTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/projet_java/modifierhebergement.fxml"));
                Parent root = loader.load();

                AdminModifierHebergementController controller = loader.getController();
                controller.setHebergement(selected);

                Stage stage = new Stage();
                stage.setTitle("Modifier Hébergement");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                // Rafraîchir la table après modification
                hebergementTable.setItems(FXCollections.observableArrayList(AdminHebergementDAO.getAllHebergements()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Veuillez sélectionner un hébergement à modifier.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void retourAccueil() {
        Stage stage = (Stage) hebergementTable.getScene().getWindow();
        com.example.projet_java.SceneSwitcher.switchScene(stage, "/com/example/projet_java/adminbienvenue.fxml", "Accueil Admin");
    }

}
