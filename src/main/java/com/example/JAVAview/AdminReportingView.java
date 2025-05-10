package com.example.JAVAview;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AdminReportingView extends JFrame {

    private com.example.JAVAcontroller.AdminReportingController controller;

    public AdminReportingView(com.example.JAVAcontroller.AdminReportingController controller) {
        this.controller = controller;
        initUI();
    }

    private void initUI() {
        setTitle("Reporting et Statistiques");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        add(createPieChartPanel());
        add(createBarChartPanel());
    }

    private JPanel createPieChartPanel() {
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        Map<String, Integer> reservations = controller.getReservationsParHebergement();
        for (Map.Entry<String, Integer> entry : reservations.entrySet()) {
            pieDataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Réservations par Hébergement",
                pieDataset,
                true, true, false
        );

        return new ChartPanel(pieChart);
    }

    private JPanel createBarChartPanel() {
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        Map<String, Double> chiffres = controller.getChiffreAffairesParMois();
        for (Map.Entry<String, Double> entry : chiffres.entrySet()) {
            barDataset.addValue(entry.getValue(), "Chiffre d'Affaires (€)", entry.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Chiffre d'Affaires par Mois",
                "Mois",
                "CA (€)",
                barDataset
        );

        return new ChartPanel(barChart);
    }
}
