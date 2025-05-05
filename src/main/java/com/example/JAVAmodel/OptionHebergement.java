package com.example.JAVAmodel;

public class OptionHebergement {
    private int idOption;
    private String nom;
    private int supplement;

    public OptionHebergement(int idOption, String nom, int supplement) {
        this.idOption = idOption;
        this.nom = nom;
        this.supplement = supplement;
    }

    public int getIdOption() {
        return idOption;
    }

    public String getNom() {
        return nom;
    }

    public int getSupplement() {
        return supplement;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setSupplement(int supplement) {
        this.supplement = supplement;
    }
}
