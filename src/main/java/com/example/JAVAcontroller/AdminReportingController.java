package com.example.JAVAcontroller;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AdminReportingController {

    private Connection connection;

    public AdminReportingController(Connection connection) {
        this.connection = connection;
    }

    // Nombre de réservations par hébergement
    public Map<String, Integer> getReservationsParHebergement() {
        Map<String, Integer> stats = new HashMap<>();
        String query = "SELECT h.nom, COUNT(r.idReservation) as nbReservations " +
                "FROM Hebergement h LEFT JOIN Reservation r ON h.idHebergement = r.idHebergement " +
                "GROUP BY h.nom";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                stats.put(rs.getString("nom"), rs.getInt("nbReservations"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stats;
    }

    // Chiffre d'affaires par mois
    public Map<String, Double> getChiffreAffairesParMois() {
        Map<String, Double> stats = new HashMap<>();
        String query = "SELECT DATE_FORMAT(dateArrivee, '%Y-%m') as mois, " +
                "SUM(t.prixAdulte * r.nbAdulte + t.prixEnfant * r.nbEnfant) as chiffreAffaires " +
                "FROM Reservation r " +
                "JOIN Hebergement h ON r.idHebergement = h.idHebergement " +
                "JOIN Tarif t ON h.idTarif = t.idTarif " +
                "GROUP BY mois " +
                "ORDER BY mois";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                stats.put(rs.getString("mois"), rs.getDouble("chiffreAffaires"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stats;
    }
}
