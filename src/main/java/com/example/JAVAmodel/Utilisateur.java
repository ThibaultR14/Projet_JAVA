package com.example.JAVAmodel;

import java.time.LocalDateTime;

/**
 * Classe représentant un utilisateur du système de réservation
 */
public class Utilisateur {
    private int idUser;
    private String nom;
    private String email;
    private String password;
    private boolean isAdmin;
    private LocalDateTime premiereConnexion;

    public Utilisateur(int idUser, String nom, String email, String password, boolean isAdmin, LocalDateTime premiereConnexion) {
        this.idUser = idUser;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.premiereConnexion = premiereConnexion;
    }

    public boolean isNouvelUtilisateur() {
        return premiereConnexion == null;
    }


    public boolean isAncienUtilisateur() {
        if (premiereConnexion == null) {
            return false; // Jamais connecté, donc pas un ancien utilisateur
        }

        // Calculer si la première connexion date d'il y a plus d'un mois
        LocalDateTime unMoisAvant = LocalDateTime.now().minusMonths(1);
        return premiereConnexion.isBefore(unMoisAvant);
    }


    public void enregistrerPremiereConnexion() {
        if (premiereConnexion == null) {
            premiereConnexion = LocalDateTime.now();
        }
    }

    // Getters et Setters
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public LocalDateTime getPremiereConnexion() {
        return premiereConnexion;
    }

    public void setPremiereConnexion(LocalDateTime premiereConnexion) {
        this.premiereConnexion = premiereConnexion;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUser=" + idUser +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin=" + isAdmin +
                ", premiereConnexion=" + premiereConnexion +
                '}';
    }
}