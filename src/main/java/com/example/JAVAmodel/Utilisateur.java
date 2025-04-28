package com.example.JAVAmodel;

public class Utilisateur {
    private int idUser;
    private String nom;
    private String email;
    private String password;
    private boolean isAdmin;

    // Constructeur par défaut
    public Utilisateur() {
    }

    // Constructeur pour créer un nouvel utilisateur (sans ID car auto-incrémenté)
    public Utilisateur(String nom, String email, String password, boolean isAdmin) {
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    // Constructeur complet (avec ID) pour récupérer un utilisateur de la BDD
    public Utilisateur(int idUser, String nom, String email, String password, boolean isAdmin) {
        this.idUser = idUser;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
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

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    // Méthode toString() pour faciliter le débogage
    @Override
    public String toString() {
        return "Utilisateur{" +
                "idUser=" + idUser +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}