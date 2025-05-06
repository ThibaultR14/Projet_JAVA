package com.example.JAVAdao;

import com.example.JAVAmodel.Reduction;
import java.sql.*;
import java.time.LocalDate;
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
                        rs.getString("description"), // ✅ ici
                        rs.getInt("pourcentage"),
                        rs.getDate("dateDebut").toLocalDate(),
                        rs.getDate("dateFin").toLocalDate()
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reductions;
    }

    public static boolean ajouterReduction(Reduction r) {
        String sql = "INSERT INTO Reduction (code, pourcentage, dateDebut, dateFin) VALUES (?, ?, ?, ?)";

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, r.getCode());
            stmt.setInt(2, r.getPourcentage());
            stmt.setDate(3, Date.valueOf(r.getDateDebut()));
            stmt.setDate(4, Date.valueOf(r.getDateFin()));

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
}
