package com.example.JAVAcontroller;

import com.example.JAVAdao.AdminReservationDAO;
import com.example.JAVAmodel.AdminReservation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.ZoneId;

public class AdminModifierReservationController {

    @FXML private TextField userField;
    @FXML private TextField hebergementField;
    @FXML private DatePicker dateArriveePicker;
    @FXML private DatePicker dateDepartPicker;
    @FXML private TextField adultesField;
    @FXML private TextField enfantsField;

    private AdminReservation reservation;

    public void setReservation(AdminReservation r) {
        this.reservation = r;
        userField.setText(r.getNomUser());
        hebergementField.setText(r.getNomHebergement());
        dateArriveePicker.setValue(r.getDateArrivee().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dateDepartPicker.setValue(r.getDateDepart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        adultesField.setText(String.valueOf(r.getNbAdulte()));
        enfantsField.setText(String.valueOf(r.getNbEnfant()));
    }

    @FXML
    private void enregistrerModification() {
        reservation.setNomUser(userField.getText());
        reservation.setNomHebergement(hebergementField.getText());
        reservation.setDateArrivee(java.sql.Timestamp.valueOf(dateArriveePicker.getValue().atStartOfDay()));
        reservation.setDateDepart(java.sql.Timestamp.valueOf(dateDepartPicker.getValue().atStartOfDay()));
        reservation.setNbAdulte(Integer.parseInt(adultesField.getText()));
        reservation.setNbEnfant(Integer.parseInt(enfantsField.getText()));

        AdminReservationDAO.updateReservation(reservation);
        ((Stage) userField.getScene().getWindow()).close();
    }
}
