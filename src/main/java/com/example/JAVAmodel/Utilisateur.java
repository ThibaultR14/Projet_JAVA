package com.example.JAVAmodel;

import java.time.LocalDate;

public class Utilisateur {
    private int idUser;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private boolean isAdmin;

    // Champs pour la carte bancaire
    private String type_carte;
    private String numeroCB;
    private String cryptogramme;
    private LocalDate expiration;

    // Constructeur pour l'inscription
    public Utilisateur(String nom, String prenom, String email, String password, boolean isAdmin) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // Constructeur pour récupération depuis la BDD
    public Utilisateur(int idUser, String nom, String prenom, String email, String password, boolean isAdmin) {
        this(nom, prenom, email, password, isAdmin);
        this.idUser = idUser;
    }

    // Getters
    public int getIdUser() { return idUser; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public boolean isAdmin() { return isAdmin; }

    public String getTypeCarte() { return type_carte; }
    public String getNumeroCB() { return numeroCB; }
    public String getCryptogramme() { return cryptogramme; }
    public LocalDate getExpiration() { return expiration; }

    // Setters
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setAdmin(boolean admin) { isAdmin = admin; }

    public void setTypeCarte(String type_carte) { this.type_carte = type_carte; }
    public void setNumeroCB(String numeroCB) { this.numeroCB = numeroCB; }
    public void setCryptogramme(String cryptogramme) { this.cryptogramme = cryptogramme; }
    public void setExpiration(LocalDate expiration) { this.expiration = expiration; }
}
