package com.example.JAVAdao;

import com.example.JAVAmodel.AdminHebergement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdminHebergementDAO {

    public static List<AdminHebergement> getAllHebergements() {
        List<AdminHebergement> hebergements = new ArrayList<>();
        try (Connection conn = AdminConnexionBDD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Hebergement")) {

            while (rs.next()) {
                AdminHebergement h = new AdminHebergement();
                h.setIdHebergement(rs.getInt("idHebergement"));
                h.setNom(rs.getString("nom"));
                h.setAdresse(rs.getString("adresse"));
                h.setNbEtoile(rs.getInt("nbEtoile"));
                h.setIdTarif(rs.getInt("idTarif"));
                h.setIdType(rs.getInt("idType"));
                h.setIdVille(rs.getInt("idVille"));
                h.setCapaciteMin(rs.getInt("capaciteMin"));
                h.setCapaciteMax(rs.getInt("capaciteMax"));
                h.setDateOuverture(rs.getDate("dateOuverture"));
                h.setDateFermeture(rs.getDate("dateFermeture"));
                h.setPhoto(rs.getString("photo"));
                hebergements.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hebergements;
    }

    public static void deleteHebergement(int id) {
        try (Connection conn = AdminConnexionBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Hebergement WHERE idHebergement = ?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateHebergement(AdminHebergement h) {
        String sql = "UPDATE Hebergement SET nom = ?, adresse = ?, nbEtoile = ?, idTarif = ?, idType = ?, idVille = ?, capaciteMin = ?, capaciteMax = ?, dateOuverture = ?, dateFermeture = ?, photo = ? WHERE idHebergement = ?";
        try (Connection conn = AdminConnexionBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, h.getNom());
            stmt.setString(2, h.getAdresse());
            stmt.setInt(3, h.getNbEtoile());
            stmt.setInt(4, h.getIdTarif());
            stmt.setInt(5, h.getIdType());
            stmt.setInt(6, h.getIdVille());
            stmt.setInt(7, h.getCapaciteMin());
            stmt.setInt(8, h.getCapaciteMax());

            java.sql.Date dateOuverture = h.getDateOuverture() != null ? new java.sql.Date(h.getDateOuverture().getTime()) : null;
            java.sql.Date dateFermeture = h.getDateFermeture() != null ? new java.sql.Date(h.getDateFermeture().getTime()) : null;

            stmt.setDate(9, dateOuverture);
            stmt.setDate(10, dateFermeture);
            stmt.setString(11, h.getPhoto());
            stmt.setInt(12, h.getIdHebergement());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
