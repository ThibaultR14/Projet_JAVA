package com.example.JAVAmodel;

import java.time.LocalDate;

public class Hebergement {
    private int idHebergement;
    private String nom;
    private String adresse;
    private int nbEtoile;
    private int capaciteMin;
    private int capaciteMax;
    private String photo;
    private int idTarif;
    private int idType;
    private int idVille;
    private LocalDate dateOuverture;
    private LocalDate dateFermeture;
    private boolean reserve;  // 🆕 Champ ajouté

    private int idUser;

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }


    // ✅ Constructeur complet
    public Hebergement(int idHebergement, String nom, String adresse, int nbEtoile, int capaciteMin,
                       int capaciteMax, String photo, int idTarif, int idType, int idVille,
                       LocalDate dateOuverture, LocalDate dateFermeture, boolean reserve) {
        this.idHebergement = idHebergement;
        this.nom = nom;
        this.adresse = adresse;
        this.nbEtoile = nbEtoile;
        this.capaciteMin = capaciteMin;
        this.capaciteMax = capaciteMax;
        this.photo = photo;
        this.idTarif = idTarif;
        this.idType = idType;
        this.idVille = idVille;
        this.dateOuverture = dateOuverture;
        this.dateFermeture = dateFermeture;
        this.reserve = reserve;
    }

    public Hebergement(int idHebergement, String nom, String adresse, int nbEtoile, int capaciteMin,
                       int capaciteMax, String photo, int idTarif, int idType, int idVille,
                       LocalDate dateOuverture, LocalDate dateFermeture, boolean reserve, int idUser) {
        this.idHebergement = idHebergement;
        this.nom = nom;
        this.adresse = adresse;
        this.nbEtoile = nbEtoile;
        this.capaciteMin = capaciteMin;
        this.capaciteMax = capaciteMax;
        this.photo = photo;
        this.idTarif = idTarif;
        this.idType = idType;
        this.idVille = idVille;
        this.dateOuverture = dateOuverture;
        this.dateFermeture = dateFermeture;
        this.reserve = reserve;
        this.idUser = idUser;
    }

    // ✅ Constructeur sans ID ni champ `reserve` (par défaut à false)
    public Hebergement(String nom, String adresse, int nbEtoile,
                       int capaciteMin, int capaciteMax,
                       String photo, int idTarif, int idVille,
                       LocalDate dateOuverture, LocalDate dateFermeture) {
        this.nom = nom;
        this.adresse = adresse;
        this.nbEtoile = nbEtoile;
        this.capaciteMin = capaciteMin;
        this.capaciteMax = capaciteMax;
        this.photo = photo;
        this.idTarif = idTarif;
        this.idVille = idVille;
        this.dateOuverture = dateOuverture;
        this.dateFermeture = dateFermeture;
        this.reserve = false;  // 🔧 Valeur par défaut
    }

    // ✅ Getters
    public int getIdHebergement() { return idHebergement; }
    public String getNom() { return nom; }
    public String getAdresse() { return adresse; }
    public int getNbEtoile() { return nbEtoile; }
    public int getCapaciteMin() { return capaciteMin; }
    public int getCapaciteMax() { return capaciteMax; }
    public String getPhoto() { return photo; }
    public int getIdTarif() { return idTarif; }
    public int getIdType() { return idType; }
    public int getIdVille() { return idVille; }
    public LocalDate getDateOuverture() { return dateOuverture; }
    public LocalDate getDateFermeture() { return dateFermeture; }
    public boolean isReserve() { return reserve; }

    // ✅ Setters
    public void setNom(String nom) { this.nom = nom; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setNbEtoile(int nbEtoile) { this.nbEtoile = nbEtoile; }
    public void setCapaciteMin(int capaciteMin) { this.capaciteMin = capaciteMin; }
    public void setCapaciteMax(int capaciteMax) { this.capaciteMax = capaciteMax; }
    public void setPhoto(String photo) { this.photo = photo; }
    public void setIdTarif(int idTarif) { this.idTarif = idTarif; }
    public void setIdType(int idType) { this.idType = idType; }
    public void setIdVille(int idVille) { this.idVille = idVille; }
    public void setDateOuverture(LocalDate dateOuverture) { this.dateOuverture = dateOuverture; }
    public void setDateFermeture(LocalDate dateFermeture) { this.dateFermeture = dateFermeture; }
    public void setReserve(boolean reserve) { this.reserve = reserve; }
}
