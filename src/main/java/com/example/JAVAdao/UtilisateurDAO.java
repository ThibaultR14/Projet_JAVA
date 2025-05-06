package com.example.JAVAdao;

import com.example.JAVAmodel.Utilisateur;

import java.sql.*;

public class UtilisateurDAO {

    public static Utilisateur getUtilisateurParEmailEtMotDePasse(String email, String password) {
        String query = "SELECT * FROM Utilisateur WHERE email = ? AND password = ?";
        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Utilisateur u = new Utilisateur(
                        rs.getInt("idUser"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBoolean("isAdmin")
                );

                // Charger les données de carte bancaire si disponibles
                u.setTypeCarte(rs.getString("type_carte"));
                u.setNumeroCB(rs.getString("numero_cb"));
                u.setCryptogramme(rs.getString("cryptogramme"));
                Date expiration = rs.getDate("expiration");
                if (expiration != null) u.setExpiration(expiration.toLocalDate());

                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean ajouterUtilisateur(Utilisateur user) {
        String query = "INSERT INTO Utilisateur (nom, prenom, email, password, isAdmin) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getPrenom());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setBoolean(5, user.isAdmin());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // doublon email probablement
            return false;
        }
    }

    public static boolean mettreAJourUtilisateur(Utilisateur user) {
        String query = "UPDATE Utilisateur SET email = ?, password = ? WHERE idUser = ?";
        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.getIdUser());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void mettreAJourCarte(Utilisateur u) {
        String sql = """
            UPDATE Utilisateur SET
                type_carte = ?,
                numero_cb = ?,
                cryptogramme = ?,
                expiration = ?
            WHERE idUser = ?
        """;

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getTypeCarte());
            stmt.setString(2, u.getNumeroCB());
            stmt.setString(3, u.getCryptogramme());
            stmt.setDate(4, u.getExpiration() != null ? Date.valueOf(u.getExpiration()) : null);
            stmt.setInt(5, u.getIdUser());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
