package com.example.JAVAdao;

import com.example.JAVAmodel.OptionHebergement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OptionHebergementDAO {

    public static List<OptionHebergement> getOptionsByHebergement(int idHebergement) {
        List<OptionHebergement> options = new ArrayList<>();
        String sql = """
            SELECT o.idOption, o.nom, o.supplement
            FROM HebergementOptions ho
            JOIN OptionsHebergement o ON ho.idOption = o.idOption
            WHERE ho.idHebergement = ?
        """;

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idHebergement);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OptionHebergement opt = new OptionHebergement(
                        rs.getInt("idOption"),
                        rs.getString("nom"),
                        rs.getInt("supplement")
                );
                options.add(opt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return options;
    }
}
