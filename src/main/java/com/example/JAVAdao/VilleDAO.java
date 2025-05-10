package com.example.JAVAdao;

import com.example.JAVAmodel.Ville;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VilleDAO {

    public static Ville getVilleById(int idVille) {
        String sql = "SELECT * FROM Ville WHERE idVille = ?";

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVille);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Ville(
                        rs.getInt("idVille"),
                        rs.getString("nom"),
                        rs.getString("codePostal")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Ville> getToutesLesVilles() {
        List<Ville> villes = new ArrayList<>();
        String sql = "SELECT * FROM Ville";

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ville v = new Ville(
                        rs.getInt("idVille"),
                        rs.getString("nom"),
                        rs.getString("codePostal")
                );
                villes.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return villes;
    }
}

