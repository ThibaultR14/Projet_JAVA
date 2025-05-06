package com.example.JAVAmodel;

import java.time.LocalDate;

public class Reservation {
    private int idReservation;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;
    private int nbAdulte;
    private int nbEnfant;
    private int idUser;
    private int idHebergement;

    public Reservation(LocalDate dateArrivee, LocalDate dateDepart, int nbAdulte, int nbEnfant, int idUser, int idHebergement) {
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.nbAdulte = nbAdulte;
        this.nbEnfant = nbEnfant;
        this.idUser = idUser;
        this.idHebergement = idHebergement;
    }

    public int getIdReservation() { return idReservation; }
    public void setIdReservation(int idReservation) { this.idReservation = idReservation; }

    public LocalDate getDateArrivee() { return dateArrivee; }
    public void setDateArrivee(LocalDate dateArrivee) { this.dateArrivee = dateArrivee; }

    public LocalDate getDateDepart() { return dateDepart; }
    public void setDateDepart(LocalDate dateDepart) { this.dateDepart = dateDepart; }

    public int getNbAdulte() { return nbAdulte; }
    public void setNbAdulte(int nbAdulte) { this.nbAdulte = nbAdulte; }

    public int getNbEnfant() { return nbEnfant; }
    public void setNbEnfant(int nbEnfant) { this.nbEnfant = nbEnfant; }

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }

    public int getIdHebergement() { return idHebergement; }
    public void setIdHebergement(int idHebergement) { this.idHebergement = idHebergement; }
}