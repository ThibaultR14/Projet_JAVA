package com.example.JAVAmodel;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;
    private int nbAdultes;
    private int nbEnfants;
    private int idUtilisateur;
    private int idHebergement;

    public Reservation(int id, LocalDate dateArrivee, LocalDate dateDepart, int nbAdultes, int nbEnfants, int idUtilisateur, int idHebergement) {
        this.id = id;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.nbAdultes = nbAdultes;
        this.nbEnfants = nbEnfants;
        this.idUtilisateur = idUtilisateur;
        this.idHebergement = idHebergement;
    }

    // Getters et Setters ici
}
