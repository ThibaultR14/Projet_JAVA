package com.example.JAVAdao;

import com.example.JAVAmodel.Reduction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReductionDAO {

    public static List<Reduction> getReductionsParUtilisateur(int idUser) {
        List<Reduction> reductions = new ArrayList<>();
        String sql = """
            SELECT r.* FROM Reduction r
            JOIN UtilisateurReduction ur ON r.idReduction = ur.idReduction
            WHERE ur.idUser = ?
        """;

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reductions.add(new Reduction(
                        rs.getInt("idReduction"),
                        rs.getString("code"),
                        rs.getString("description"),
                        rs.getInt("pourcentage"),
                        rs.getDate("dateDebut") != null ? rs.getDate("dateDebut").toLocalDate() : null,
                        rs.getDate("dateFin") != null ? rs.getDate("dateFin").toLocalDate() : null
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reductions;
    }

    public static boolean ajouterReduction(Reduction r) {
        String sql = "INSERT INTO Reduction (code, description, pourcentage, dateDebut, dateFin) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, r.getCode());
            stmt.setString(2, r.getDescription());
            stmt.setInt(3, r.getPourcentage());
            stmt.setDate(4, Date.valueOf(r.getDateDebut()));
            stmt.setDate(5, Date.valueOf(r.getDateFin()));

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) r.setIdReduction(rs.getInt(1));
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void assignerReductionUtilisateur(int idUser, int idReduction) {
        String sql = "INSERT INTO UtilisateurReduction (idUser, idReduction) VALUES (?, ?)";

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            stmt.setInt(2, idReduction);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Nouvelle méthode : rechercher une réduction par code
    public static Reduction getReductionByCode(String code) {
        String sql = "SELECT * FROM Reduction WHERE code = ?";

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Reduction(
                        rs.getInt("idReduction"),
                        rs.getString("code"),
                        rs.getString("description"),
                        rs.getInt("pourcentage"),
                        rs.getDate("dateDebut") != null ? rs.getDate("dateDebut").toLocalDate() : null,
                        rs.getDate("dateFin") != null ? rs.getDate("dateFin").toLocalDate() : null
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Reduction getReductionByCodeAndUser(String code, int idUser) {
        String sql = """
        SELECT r.* FROM Reduction r
        JOIN UtilisateurReduction ur ON r.idReduction = ur.idReduction
        WHERE r.code = ? AND ur.idUser = ?
    """;

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, code);
            stmt.setInt(2, idUser);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Reduction(
                        rs.getInt("idReduction"),
                        rs.getString("code"),
                        rs.getString("description"),
                        rs.getInt("pourcentage"),
                        rs.getDate("dateDebut") != null ? rs.getDate("dateDebut").toLocalDate() : null,
                        rs.getDate("dateFin") != null ? rs.getDate("dateFin").toLocalDate() : null
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
