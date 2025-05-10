package com.example.JAVAcontroller;

import com.example.projet_java.SceneSwitcher;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class FooterController {

    @FXML
    private Label creditsLabel;

    @FXML
    public void initialize() {
        creditsLabel.setCursor(Cursor.HAND);
    }

    @FXML
    public void onCreditsClicked(MouseEvent event) {
        SceneSwitcher.switchScene(
                (Stage) ((Node) event.getSource()).getScene().getWindow(),
                "/com/example/projet_java/credits.fxml",
                "Crédits"
        );
    }
}
