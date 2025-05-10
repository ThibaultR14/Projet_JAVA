package com.example.JAVAmodel;

public class Tarif {
    private int idTarif;
    private int prixAdulte;
    private int prixEnfant;
    private int prixVIP;

    public Tarif(int idTarif, int prixAdulte, int prixEnfant, int prixVIP) {
        this.idTarif = idTarif;
        this.prixAdulte = prixAdulte;
        this.prixEnfant = prixEnfant;
        this.prixVIP = prixVIP;
    }

    public Tarif(int prixAdulte, int prixEnfant, int prixVIP) {
        this.prixAdulte = prixAdulte;
        this.prixEnfant = prixEnfant;
        this.prixVIP = prixVIP;
    }


    // Getters
    public int getIdTarif() { return idTarif; }
    public int getPrixAdulte() { return prixAdulte; }
    public int getPrixEnfant() { return prixEnfant; }
    public int getPrixVIP() { return prixVIP; }

    // Setters
    public void setPrixAdulte(int prixAdulte) { this.prixAdulte = prixAdulte; }
    public void setPrixEnfant(int prixEnfant) { this.prixEnfant = prixEnfant; }
    public void setPrixVIP(int prixVIP) { this.prixVIP = prixVIP; }
}
