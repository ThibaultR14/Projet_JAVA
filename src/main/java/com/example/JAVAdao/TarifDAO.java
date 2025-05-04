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
}
