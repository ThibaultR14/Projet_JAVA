package com.example.JAVAdao;

import com.example.JAVAmodel.AdminReduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminReductionDAO {

    public List<AdminReduction> getReductionsAvecUtilisateurs() {
        List<AdminReduction> list = new ArrayList<>();

        String sql = """
            SELECT r.code, r.pourcentage, r.description, r.dateDebut, r.dateFin, u.nom AS utilisateur
            FROM Reduction r
            JOIN UtilisateurReduction ur ON r.idReduction = ur.idReduction
            JOIN Utilisateur u ON u.idUser = ur.idUser
        """;

        try (Connection conn = AdminConnexionBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new AdminReduction(
                        rs.getString("utilisateur"),
                        rs.getString("code"),
                        rs.getInt("pourcentage"),
                        rs.getString("description"),
                        rs.getDate("dateDebut") != null ? rs.getDate("dateDebut").toString() : "",
                        rs.getDate("dateFin") != null ? rs.getDate("dateFin").toString() : ""
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public boolean deleteReductionByCode(String code) {
        String sql = "DELETE FROM Reduction WHERE code = ?";
        try (Connection conn = AdminConnexionBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, code);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int insertReduction(String code, int pourcentage, String description, String dateDebut, String dateFin) {
        String sql = "INSERT INTO Reduction (code, pourcentage, description, dateDebut, dateFin) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = AdminConnexionBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, code);
            stmt.setInt(2, pourcentage);
            stmt.setString(3, description);
            stmt.setDate(4, java.sql.Date.valueOf(dateDebut));
            stmt.setDate(5, java.sql.Date.valueOf(dateFin));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean associerReductionAUtilisateur(int idReduction, int idUser) {
        String sql = "INSERT INTO UtilisateurReduction (idUser, idReduction) VALUES (?, ?)";

        try (Connection conn = AdminConnexionBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            stmt.setInt(2, idReduction);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
