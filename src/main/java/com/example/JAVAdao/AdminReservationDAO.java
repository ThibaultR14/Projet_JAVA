package com.example.JAVAdao;

import com.example.JAVAmodel.AdminReservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class AdminReservationDAO {

    public static List<AdminReservation> getAllReservations() {
        List<AdminReservation> list = new ArrayList<>();
        String sql = """
        SELECT r.*, u.nom AS nomUser, h.nom AS nomHebergement
        FROM Reservation r
        JOIN Utilisateur u ON r.idUser = u.idUser
        JOIN Hebergement h ON r.idHebergement = h.idHebergement
    """;

        try (Connection conn = AdminConnexionBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AdminReservation r = new AdminReservation();
                r.setIdReservation(rs.getInt("idReservation"));
                r.setDateArrivee(rs.getTimestamp("dateArrivee"));
                r.setDateDepart(rs.getTimestamp("dateDepart"));
                r.setNbAdulte(rs.getInt("nbAdulte"));
                r.setNbEnfant(rs.getInt("nbEnfant"));
                r.setIdUser(rs.getInt("idUser"));
                r.setIdHebergement(rs.getInt("idHebergement"));

                // Ces champs ne sont pas dans la table Reservation mais ajoutés via JOIN
                r.setNomUser(rs.getString("nomUser"));
                r.setNomHebergement(rs.getString("nomHebergement"));

                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public static void updateReservation(AdminReservation r) {
        String sql = "UPDATE Reservation SET dateArrivee = ?, dateDepart = ?, nbAdulte = ?, nbEnfant = ?, idUser = ?, idHebergement = ? WHERE idReservation = ?";
        try (Connection conn = AdminConnexionBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, new java.sql.Timestamp(r.getDateArrivee().getTime()));
            stmt.setTimestamp(2, new java.sql.Timestamp(r.getDateDepart().getTime()));
            stmt.setInt(3, r.getNbAdulte());
            stmt.setInt(4, r.getNbEnfant());
            stmt.setInt(5, r.getIdUser());
            stmt.setInt(6, r.getIdHebergement());
            stmt.setInt(7, r.getIdReservation());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteReservation(int idReservation) {
        String sql = "DELETE FROM Reservation WHERE idReservation = ?";
        try (Connection conn = AdminConnexionBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idReservation);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Map<String, Integer> getReservationsParMois() {
        Map<String, Integer> data = new LinkedHashMap<>();
        String sql = "SELECT MONTHNAME(dateArrivee) AS mois, COUNT(*) AS total " +
                "FROM Reservation GROUP BY mois ORDER BY MONTH(dateArrivee)";

        try (Connection conn = AdminConnexionBDD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                data.put(rs.getString("mois"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static Map<String, Integer> getNbAdultesEtEnfants() {
        Map<String, Integer> data = new HashMap<>();
        String sql = "SELECT SUM(nbAdulte) AS adultes, SUM(nbEnfant) AS enfants FROM Reservation";

        try (Connection conn = AdminConnexionBDD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                data.put("Adultes", rs.getInt("adultes"));
                data.put("Enfants", rs.getInt("enfants"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }


}
