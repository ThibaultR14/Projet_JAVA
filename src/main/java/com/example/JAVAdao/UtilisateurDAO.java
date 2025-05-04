package com.example.JAVAdao;

import com.example.JAVAmodel.Utilisateur;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.example.JAVAcontroller.ConnexionController;
import javafx.scene.control.Alert;

/**
 * Implémentation de l'interface UtilisateurDAO pour les opérations sur les utilisateurs
 */
public class UtilisateurDAO {

    private Connection connexion;

    /**
     * Constructeur avec connexion à la base de données
     *
     * @param connexion La connexion JDBC à la base de données
     */
    public UtilisateurDAO(Connection connexion) {
        this.connexion = connexion;
    }

    public int ajouterUtilisateur(Utilisateur utilisateur) {
        String sql = "INSERT INTO Utilisateur (nom, email, password, isAdmin) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getEmail());
            pstmt.setString(3, utilisateur.getPassword());
            pstmt.setBoolean(4, utilisateur.isAdmin());

            int lignesAffectees = pstmt.executeUpdate();

            if (lignesAffectees > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        }

        return -1;
    }

    public boolean mettreAJourUtilisateur(Utilisateur utilisateur) {
        String sql = "UPDATE Utilisateur SET nom = ?, email = ?, password = ?, isAdmin = ?, premiereConnexion = ? WHERE idUser = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getEmail());
            pstmt.setString(3, utilisateur.getPassword());
            pstmt.setBoolean(4, utilisateur.isAdmin());

            if (utilisateur.getPremiereConnexion() != null) {
                pstmt.setTimestamp(5, Timestamp.valueOf(utilisateur.getPremiereConnexion()));
            } else {
                pstmt.setNull(5, java.sql.Types.TIMESTAMP);
            }

            pstmt.setInt(6, utilisateur.getIdUser());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
            return false;
        }
    }

    public boolean supprimerUtilisateur(int idUser) {
        String sql = "DELETE FROM Utilisateur WHERE idUser = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, idUser);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
            return false;
        }
    }

    public Utilisateur trouverParId(int idUser) {
        String sql = "SELECT * FROM Utilisateur WHERE idUser = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, idUser);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extraireUtilisateur(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'utilisateur par ID : " + e.getMessage());
        }

        return null;
    }

    public Utilisateur trouverParEmail(String email) {
        String sql = "SELECT * FROM Utilisateur WHERE email = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return extraireUtilisateur(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'utilisateur par email : " + e.getMessage());
        }

        return null;
    }

    public Utilisateur connecter(String email, String password) {
        String sql = "SELECT * FROM Utilisateur WHERE email = ? AND password = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Utilisateur utilisateur = extraireUtilisateur(rs);

                // Si c'est la première connexion, on l'enregistre
                if (utilisateur.isNouvelUtilisateur()) {
                    enregistrerPremiereConnexion(utilisateur.getIdUser());
                    utilisateur.setPremiereConnexion(LocalDateTime.now());
                }

                return utilisateur;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion de l'utilisateur : " + e.getMessage());
        }

        return null;
    }

    public boolean enregistrerPremiereConnexion(int idUser) {
        String sql = "UPDATE Utilisateur SET premiereConnexion = ? WHERE idUser = ? AND premiereConnexion IS NULL";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(2, idUser);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement de la première connexion : " + e.getMessage());
            return false;
        }
    }

    public boolean estNouvelUtilisateur(int idUser) {
        String sql = "SELECT premiereConnexion FROM Utilisateur WHERE idUser = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, idUser);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Si premiereConnexion est NULL, c'est un nouvel utilisateur
                return rs.getTimestamp("premiereConnexion") == null;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification du statut de l'utilisateur : " + e.getMessage());
        }

        return false;
    }

    public boolean estAncienUtilisateur(int idUser) {
        String sql = "SELECT premiereConnexion FROM Utilisateur WHERE idUser = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setInt(1, idUser);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Timestamp premiereConnexionTs = rs.getTimestamp("premiereConnexion");

                // Si premiereConnexion est NULL, ce n'est pas un ancien utilisateur
                if (premiereConnexionTs == null) {
                    return false;
                }

                // Vérifier si la première connexion date d'il y a plus d'un mois
                LocalDateTime premiereConnexion = premiereConnexionTs.toLocalDateTime();
                LocalDateTime unMoisAvant = LocalDateTime.now().minusMonths(1);

                return premiereConnexion.isBefore(unMoisAvant);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification si l'utilisateur est ancien : " + e.getMessage());
        }

        return false;
    }

    public List<Utilisateur> listerTousUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM Utilisateur";

        try (Statement stmt = connexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                utilisateurs.add(extraireUtilisateur(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de tous les utilisateurs : " + e.getMessage());
        }

        return utilisateurs;
    }

    private Utilisateur extraireUtilisateur(ResultSet rs) throws SQLException {
        int idUser = rs.getInt("idUser");
        String nom = rs.getString("nom");
        String email = rs.getString("email");
        String password = rs.getString("password");
        boolean isAdmin = rs.getBoolean("isAdmin");

        // Traitement spécial pour la date de première connexion qui peut être NULL
        Timestamp premiereConnexionTs = rs.getTimestamp("premiereConnexion");
        LocalDateTime premiereConnexion = premiereConnexionTs != null ? premiereConnexionTs.toLocalDateTime() : null;

        return new Utilisateur(idUser, nom, email, password, isAdmin, premiereConnexion);
    }

    public boolean verifierUtilisateur(String email, String password) {
        String sql = "SELECT 1 FROM utilisateurs WHERE email = ? AND password = ?";

        try (PreparedStatement pstmt = connexion.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // retourne true si une ligne correspond

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e.getMessage());
            return false;
        }
    }

}