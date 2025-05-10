package com.example.JAVAmodel;

import java.util.Date;

public class AdminReservation {
    private int idReservation;
    private Date dateArrivee;
    private Date dateDepart;
    private int nbAdulte;
    private int nbEnfant;
    private int idUser;
    private int idHebergement;
    private String nomUser;
    private String nomHebergement;

    // Getters
    public int getIdReservation() { return idReservation; }
    public Date getDateArrivee() { return dateArrivee; }
    public Date getDateDepart() { return dateDepart; }
    public int getNbAdulte() { return nbAdulte; }
    public int getNbEnfant() { return nbEnfant; }
    public int getIdUser() { return idUser; }
    public int getIdHebergement() { return idHebergement; }
    public String getNomUser() { return nomUser; }
    public String getNomHebergement() { return nomHebergement; }

    // Setters
    public void setIdReservation(int idReservation) { this.idReservation = idReservation; }
    public void setDateArrivee(Date dateArrivee) { this.dateArrivee = dateArrivee; }
    public void setDateDepart(Date dateDepart) { this.dateDepart = dateDepart; }
    public void setNbAdulte(int nbAdulte) { this.nbAdulte = nbAdulte; }
    public void setNbEnfant(int nbEnfant) { this.nbEnfant = nbEnfant; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    public void setIdHebergement(int idHebergement) { this.idHebergement = idHebergement; }
    public void setNomUser(String nomUser) { this.nomUser = nomUser; }
    public void setNomHebergement(String nomHebergement) { this.nomHebergement = nomHebergement; }
}
