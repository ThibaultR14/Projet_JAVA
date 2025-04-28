package com.example.JAVAmodel;

public class Hebergement {
    private int id;
    private String nom;
    private String adresse;
    private int nbEtoiles;
    private double prixParNuit;
    private String type;
    private String ville;

    public Hebergement(int id, String nom, String adresse, int nbEtoiles, double prixParNuit, String type, String ville) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.nbEtoiles = nbEtoiles;
        this.prixParNuit = prixParNuit;
        this.type = type;
        this.ville = ville;
    }

    // Getters et Setters ici
}
