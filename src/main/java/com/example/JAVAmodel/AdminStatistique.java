package com.example.JAVAmodel;

public class AdminStatistique {
    private String nom;
    private double valeur;

    public AdminStatistique(String nom, double valeur) {
        this.nom = nom;
        this.valeur = valeur;
    }

    public String getNom() {
        return nom;
    }

    public double getValeur() {
        return valeur;
    }
}
