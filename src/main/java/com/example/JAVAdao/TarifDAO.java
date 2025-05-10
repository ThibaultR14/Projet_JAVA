package com.example.JAVAdao;

import com.example.JAVAmodel.Tarif;
import java.sql.*;

public class TarifDAO {

    public static Tarif getTarifById(int idTarif) {
        String query = "SELECT * FROM Tarif WHERE idTarif = ?";
        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idTarif);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Tarif(
                        idTarif,
                        rs.getInt("prixAdulte"),
                        rs.getInt("prixEnfant"),
                        rs.getInt("prixVIP")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int ajouterEtRetournerId(Tarif tarif) {
        int id = -1;
        String sql = "INSERT INTO Tarif (prixAdulte, prixEnfant, prixVIP) VALUES (?, ?, ?)";

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, tarif.getPrixAdulte());
            stmt.setInt(2, tarif.getPrixEnfant());
            stmt.setInt(3, tarif.getPrixVIP());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

}
