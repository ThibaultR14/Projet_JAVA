package com.example.JAVAdao;

import com.example.JAVAmodel.Hebergement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class HebergementDAO {

    // Méthode pour récupérer tous les hébergements
    public List<Hebergement> findAll() {
        List<Hebergement> hebergements = new ArrayList<>();
        String requete = "SELECT * FROM Hebergement";

        try (Connection connexion = connexionbdd.getConnection();
             Statement stmt = connexion.createStatement();
             ResultSet rs = stmt.executeQuery(requete)) {

            while (rs.next()) {
                Hebergement hebergement = mapResultSetToHebergement(rs);
                hebergements.add(hebergement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hebergements;
    }

    // Méthode pour chercher les hébergements par critères
    public List<Hebergement> findByCriteria(String ville, int prixMax, int nbEtoiles) {
        List<Hebergement> hebergements = new ArrayList<>();
        String requete = "SELECT H.* FROM Hebergement H " +
                "JOIN Ville V ON H.idVille = V.idVille " +
                "JOIN Tarif T ON H.idTarif = T.idTarif " +
                "WHERE V.nom LIKE ? AND T.prixAdulte <= ? AND H.nbEtoile >= ?";

        try (Connection connexion = connexionbdd.getConnection();
             PreparedStatement pstmt = connexion.prepareStatement(requete)) {

            pstmt.setString(1, "%" + ville + "%");
            pstmt.setInt(2, prixMax);
            pstmt.setInt(3, nbEtoiles);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Hebergement hebergement = mapResultSetToHebergement(rs);
                hebergements.add(hebergement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hebergements;
    }

    // Méthode pour insérer un nouvel hébergement
    public boolean insert(Hebergement hebergement) {
        String requete = "INSERT INTO Hebergement (nom, adresse, nbEtoile, capaciteMin, capaciteMax, idTarif, idType, idVille, dateOuverture, dateFermeture) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connexion = connexionbdd.getConnection();
             PreparedStatement pstmt = connexion.prepareStatement(requete)) {

            pstmt.setString(1, hebergement.getNom());
            pstmt.setString(2, hebergement.getAdresse());
            pstmt.setInt(3, hebergement.getNbEtoile());
            pstmt.setInt(4, hebergement.getCapaciteMin());
            pstmt.setInt(5, hebergement.getCapaciteMax());
            pstmt.setInt(6, hebergement.getIdTarif());
            pstmt.setInt(7, hebergement.getIdType());
            pstmt.setInt(8, hebergement.getIdVille());
            pstmt.setDate(9, Date.valueOf(hebergement.getDateOuverture())); // Nouveau
            pstmt.setDate(10, Date.valueOf(hebergement.getDateFermeture())); // Nouveau

            int lignesAffectees = pstmt.executeUpdate();
            return lignesAffectees > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour supprimer un hébergement par id
    public boolean delete(int idHebergement) {
        String requete = "DELETE FROM Hebergement WHERE idHebergement = ?";

        try (Connection connexion = connexionbdd.getConnection();
             PreparedStatement pstmt = connexion.prepareStatement(requete)) {

            pstmt.setInt(1, idHebergement);

            int lignesAffectees = pstmt.executeUpdate();
            return lignesAffectees > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Méthode pour rechercher les hébergements disponibles par ville, date et capacité
    public List<Hebergement> rechercherParCriteres(String ville, LocalDate dateArrivee, LocalDate dateDepart, int nbAdultes, int nbEnfants) {
        List<Hebergement> hebergements = new ArrayList<>();
        String requete = "SELECT H.* FROM Hebergement H " +
                "JOIN Ville V ON H.idVille = V.idVille " +
                "WHERE V.nom LIKE ? " +
                "AND H.capaciteMin <= ? AND H.capaciteMax >= ? " +
                "AND H.dateOuverture <= ? " +
                "AND H.dateFermeture >= ?";

        try (Connection connexion = connexionbdd.getConnection();
             PreparedStatement pstmt = connexion.prepareStatement(requete)) {

            pstmt.setString(1, "%" + ville + "%");
            pstmt.setInt(2, nbAdultes + nbEnfants); // Nombre total de personnes
            pstmt.setInt(3, nbAdultes + nbEnfants);
            pstmt.setDate(4, Date.valueOf(dateArrivee)); // Date d'arrivée
            pstmt.setDate(5, Date.valueOf(dateDepart));  // Date de départ

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Hebergement hebergement = mapResultSetToHebergement(rs);
                hebergements.add(hebergement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hebergements;
    }


    // Méthode utilitaire pour convertir ResultSet en objet Hebergement
    private Hebergement mapResultSetToHebergement(ResultSet rs) throws SQLException {
        return new Hebergement(
                rs.getInt("idHebergement"),
                rs.getString("nom"),
                rs.getString("adresse"),
                rs.getInt("nbEtoile"),
                rs.getInt("capaciteMin"),
                rs.getInt("capaciteMax"),
                rs.getInt("idTarif"),
                rs.getInt("idType"),
                rs.getInt("idVille"),
                rs.getDate("dateOuverture").toLocalDate(), // Nouveau
                rs.getDate("dateFermeture").toLocalDate()   // Nouveau
        );
    }
}
