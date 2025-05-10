package com.example.JAVAmodel;

import java.util.Date;

public class AdminHebergement {
    private int idHebergement;
    private String nom;
    private String adresse;
    private int nbEtoile;
    private int idTarif;
    private int idType;
    private int idVille;
    private int capaciteMin;
    private int capaciteMax;
    private Date dateOuverture;
    private Date dateFermeture;
    private String photo;

    // Getters et Setters
    public int getIdHebergement() {
        return idHebergement;
    }

    public void setIdHebergement(int idHebergement) {
        this.idHebergement = idHebergement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNbEtoile() {
        return nbEtoile;
    }

    public void setNbEtoile(int nbEtoile) {
        this.nbEtoile = nbEtoile;
    }

    public int getIdTarif() {
        return idTarif;
    }

    public void setIdTarif(int idTarif) {
        this.idTarif = idTarif;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getIdVille() {
        return idVille;
    }

    public void setIdVille(int idVille) {
        this.idVille = idVille;
    }

    public int getCapaciteMin() {
        return capaciteMin;
    }

    public void setCapaciteMin(int capaciteMin) {
        this.capaciteMin = capaciteMin;
    }

    public int getCapaciteMax() {
        return capaciteMax;
    }

    public void setCapaciteMax(int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    public Date getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(Date dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public Date getDateFermeture() {
        return dateFermeture;
    }

    public void setDateFermeture(Date dateFermeture) {
        this.dateFermeture = dateFermeture;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
