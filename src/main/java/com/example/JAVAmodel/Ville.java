package com.example.JAVAmodel;

public class Ville {
    private int idVille;
    private String nom;
    private String codePostal;

    public Ville(int idVille, String nom, String codePostal) {
        this.idVille = idVille;
        this.nom = nom;
        this.codePostal = codePostal;
    }

    public int getIdVille() {
        return idVille;
    }

    public String getNom() {
        return nom;
    }

    public String getCodePostal() {
        return codePostal;
    }

    @Override
    public String toString() {
        return nom + " (" + codePostal + ")";
    }
}
