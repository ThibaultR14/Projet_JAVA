package com.example.JAVAmodel;

public class AdminReduction {
    private String utilisateur;
    private String code;
    private int pourcentage;
    private String description;
    private String dateDebut;
    private String dateFin;

    // Constructeur
    public AdminReduction(String utilisateur, String code, int pourcentage, String description, String dateDebut, String dateFin) {
        this.utilisateur = utilisateur;
        this.code = code;
        this.pourcentage = pourcentage;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // Getters
    public String getUtilisateur() { return utilisateur; }
    public String getCode() { return code; }
    public int getPourcentage() { return pourcentage; }
    public String getDescription() { return description; }
    public String getDateDebut() { return dateDebut; }
    public String getDateFin() { return dateFin; }
}
