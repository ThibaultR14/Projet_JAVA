package com.example.JAVAcontroller;

import com.example.JAVAdao.AdminReductionDAO;
import com.example.JAVAdao.AdminUtilisateurDAO;
import com.example.JAVAmodel.AdminReduction;
import com.example.JAVAmodel.AdminUtilisateur;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.example.projet_java.SceneSwitcher;

public class AdminReductionController {

    @FXML private TextField fieldCode;
    @FXML private TextField fieldPourcentage;
    @FXML private TextField fieldDescription;
    @FXML private DatePicker pickerDebut;
    @FXML private DatePicker pickerFin;
    @FXML private TextField fieldUserId;

    @FXML private TableView<AdminReduction> tableReductions;
    @FXML private TableColumn<AdminReduction, String> colUtilisateur;
    @FXML private TableColumn<AdminReduction, String> colCode;
    @FXML private TableColumn<AdminReduction, Integer> colPourcentage;
    @FXML private TableColumn<AdminReduction, String> colDescription;
    @FXML private TableColumn<AdminReduction, String> colDateDebut;
    @FXML private TableColumn<AdminReduction, String> colDateFin;

    @FXML private TableView<AdminUtilisateur> tableUtilisateurs;
    @FXML private TableColumn<AdminUtilisateur, Integer> colUserId;
    @FXML private TableColumn<AdminUtilisateur, String> colUserNom;

    private final AdminReductionDAO dao = new AdminReductionDAO();
    private final AdminUtilisateurDAO utilisateurDAO = new AdminUtilisateurDAO();

    @FXML
    public void initialize() {
        // Initialisation du tableau de réductions
        colUtilisateur.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getUtilisateur()));
        colCode.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCode()));
        colPourcentage.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getPourcentage()).asObject());
        colDescription.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDescription()));
        colDateDebut.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDateDebut()));
        colDateFin.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDateFin()));
        tableReductions.setItems(FXCollections.observableArrayList(dao.getReductionsAvecUtilisateurs()));

        // Initialisation du tableau des utilisateurs
        colUserId.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getIdUser()).asObject());
        colUserNom.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNom()));
        tableUtilisateurs.setItems(FXCollections.observableArrayList(utilisateurDAO.getAllUtilisateurs()));

        // Remplissage automatique du champ ID utilisateur lors de la sélection
        tableUtilisateurs.setOnMouseClicked(event -> {
            AdminUtilisateur selectedUser = tableUtilisateurs.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                fieldUserId.setText(String.valueOf(selectedUser.getIdUser()));
            }
        });
    }

    @FXML
    public void handleAjouterReduction() {
        try {
            String code = fieldCode.getText();
            int pourcentage = Integer.parseInt(fieldPourcentage.getText());
            String description = fieldDescription.getText();
            String dateDebut = pickerDebut.getValue().toString();
            String dateFin = pickerFin.getValue().toString();
            int userId = Integer.parseInt(fieldUserId.getText());

            int idReduction = dao.insertReduction(code, pourcentage, description, dateDebut, dateFin);
            if (idReduction > 0) {
                boolean associe = dao.associerReductionAUtilisateur(idReduction, userId);
                if (associe) {
                    tableReductions.setItems(FXCollections.observableArrayList(dao.getReductionsAvecUtilisateurs()));
                    clearForm();
                } else {
                    showAlert("Erreur", "Réduction créée mais non associée à l'utilisateur.");
                }
            } else {
                showAlert("Erreur", "Échec de la création de la réduction.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Vérifiez les champs saisis.");
        }
    }

    @FXML
    public void handleSupprimerReduction() {
        AdminReduction selected = tableReductions.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean success = dao.deleteReductionByCode(selected.getCode());
            if (success) {
                tableReductions.getItems().remove(selected);
            } else {
                showAlert("Erreur", "Échec de la suppression.");
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner une réduction à supprimer.");
        }
    }

    @FXML
    public void handleRetourMenu() {
        Stage stage = (Stage) tableReductions.getScene().getWindow();
        SceneSwitcher.switchScene(stage, "/com/example/projet_java/adminbienvenue.fxml", "Accueil Admin");
    }

    private void clearForm() {
        fieldCode.clear();
        fieldPourcentage.clear();
        fieldDescription.clear();
        pickerDebut.setValue(null);
        pickerFin.setValue(null);
        fieldUserId.clear();
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
