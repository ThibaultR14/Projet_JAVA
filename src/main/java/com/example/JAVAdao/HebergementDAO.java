package com.example.JAVAdao;

import com.example.JAVAmodel.Hebergement;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HebergementDAO {

    public static List<Hebergement> getTousLesHebergements() {
        List<Hebergement> hebergements = new ArrayList<>();
        String query = "SELECT * FROM Hebergement";

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                hebergements.add(mapHebergement(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hebergements;
    }

    public static List<Hebergement> findAll() {
        List<Hebergement> hebergements = new ArrayList<>();
        String query = "SELECT * FROM Hebergement";

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                hebergements.add(mapHebergement(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hebergements;
    }

    public static List<Hebergement> findByCriteria(String ville, Integer nbAdultes, Integer nbEnfants,
                                                   LocalDate dateArrivee, LocalDate dateDepart) {
        List<Hebergement> hebergements = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
        SELECT h.* FROM Hebergement h
        JOIN Ville v ON h.idVille = v.idVille
        WHERE 1 = 1
    """);

        List<Object> params = new ArrayList<>();

        if (ville != null && !ville.isBlank()) {
            sql.append(" AND LOWER(v.nom) LIKE ? ");
            params.add("%" + ville.toLowerCase() + "%");
        }

        int totalPersonnes = (nbAdultes != null ? nbAdultes : 0) + (nbEnfants != null ? nbEnfants : 0);
        if (totalPersonnes > 0) {
            sql.append(" AND h.capaciteMax >= ? ");
            params.add(totalPersonnes);
        }

        if (dateArrivee != null) {
            sql.append(" AND (h.dateOuverture IS NULL OR h.dateOuverture <= ?) ");
            params.add(Date.valueOf(dateArrivee));
        }

        if (dateDepart != null) {
            sql.append(" AND (h.dateFermeture IS NULL OR h.dateFermeture >= ?) ");
            params.add(Date.valueOf(dateDepart));
        }

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Hebergement h = new Hebergement(
                        rs.getInt("idHebergement"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getInt("nbEtoile"),
                        rs.getInt("capaciteMin"),
                        rs.getInt("capaciteMax"),
                        rs.getString("photo"),
                        rs.getInt("idTarif"),
                        rs.getInt("idType"),
                        rs.getInt("idVille"),
                        rs.getDate("dateOuverture") != null ? rs.getDate("dateOuverture").toLocalDate() : null,
                        rs.getDate("dateFermeture") != null ? rs.getDate("dateFermeture").toLocalDate() : null
                );
                hebergements.add(h);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hebergements;
    }


    public static boolean insert(Hebergement h) {
        String query = """
            INSERT INTO Hebergement
            (nom, adresse, nbEtoile, capaciteMin, capaciteMax, photo, idTarif, idType, idVille, dateOuverture, dateFermeture)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, h.getNom());
            stmt.setString(2, h.getAdresse());
            stmt.setInt(3, h.getNbEtoile());
            stmt.setInt(4, h.getCapaciteMin());
            stmt.setInt(5, h.getCapaciteMax());
            stmt.setString(6, h.getPhoto());
            stmt.setInt(7, h.getIdTarif());
            stmt.setInt(8, h.getIdType());
            stmt.setInt(9, h.getIdVille());
            stmt.setDate(10, h.getDateOuverture() != null ? Date.valueOf(h.getDateOuverture()) : null);
            stmt.setDate(11, h.getDateFermeture() != null ? Date.valueOf(h.getDateFermeture()) : null);

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delete(int idHebergement) {
        String query = "DELETE FROM Hebergement WHERE idHebergement = ?";

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idHebergement);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Méthode utilitaire commune pour créer un Hebergement à partir du ResultSet
    private static Hebergement mapHebergement(ResultSet rs) throws SQLException {
        return new Hebergement(
                rs.getInt("idHebergement"),
                rs.getString("nom"),
                rs.getString("adresse"),
                rs.getInt("nbEtoile"),
                rs.getInt("capaciteMin"),
                rs.getInt("capaciteMax"),
                rs.getString("photo"),
                rs.getInt("idTarif"),
                rs.getInt("idType"),
                rs.getInt("idVille"),
                rs.getDate("dateOuverture") != null ? rs.getDate("dateOuverture").toLocalDate() : null,
                rs.getDate("dateFermeture") != null ? rs.getDate("dateFermeture").toLocalDate() : null
        );
    }
}
