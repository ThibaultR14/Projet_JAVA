package com.example.JAVAcontroller;

import com.example.JAVAdao.AdminHebergementDAO;
import com.example.JAVAmodel.AdminHebergement;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminModifierHebergementController {

    @FXML private TextField nomField;
    @FXML private TextField adresseField;
    @FXML private TextField etoilesField;

    private AdminHebergement hebergement;

    public void setHebergement(AdminHebergement h) {
        this.hebergement = h;
        nomField.setText(h.getNom());
        adresseField.setText(h.getAdresse());
        etoilesField.setText(String.valueOf(h.getNbEtoile()));
    }

    @FXML
    private void enregistrerModification() {
        hebergement.setNom(nomField.getText());
        hebergement.setAdresse(adresseField.getText());
        hebergement.setNbEtoile(Integer.parseInt(etoilesField.getText()));

        AdminHebergementDAO.updateHebergement(hebergement);
        ((Stage) nomField.getScene().getWindow()).close();
    }
}
