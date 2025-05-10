package com.example.JAVAcontroller;

import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import com.example.JAVAdao.AdminReservationDAO;

import java.util.Map;

public class AdminStatisticsController {

    @FXML
    private VBox chartContainer;

    @FXML
    public void initialize() {
        chartContainer.getChildren().add(createBarChart());
        chartContainer.getChildren().add(createPieChart());
    }

    private BarChart<String, Number> createBarChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Mois");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Nombre de réservations");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Réservations par mois");

        barChart.getStyleClass().add("chart-title");


        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Réservations");

        Map<String, Integer> stats = AdminReservationDAO.getReservationsParMois();
        stats.forEach((mois, total) -> series.getData().add(new XYChart.Data<>(mois, total)));

        barChart.getData().add(series);
        barChart.setPrefSize(600, 350);
        return barChart;
    }

    private PieChart createPieChart() {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Répartition Adultes / Enfants");

        pieChart.getStyleClass().add("chart-title");


        Map<String, Integer> stats = AdminReservationDAO.getNbAdultesEtEnfants();
        stats.forEach((label, value) -> pieChart.getData().add(new PieChart.Data(label, value)));

        pieChart.setPrefSize(600, 350);
        return pieChart;
    }
}
