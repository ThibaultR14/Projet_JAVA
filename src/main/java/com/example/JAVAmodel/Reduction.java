package com.example.JAVAmodel;

import java.time.LocalDate;

public class Reduction {
    private int idReduction;
    private String code;
    private String description; // ✅ Ajout
    private int pourcentage;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    public Reduction(int idReduction, String code, int pourcentage, LocalDate dateDebut, LocalDate dateFin) {
        this.idReduction = idReduction;
        this.code = code;
        this.pourcentage = pourcentage;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }


    public Reduction(String code, String description, int pourcentage, LocalDate dateDebut, LocalDate dateFin) {
        this.code = code;
        this.description = description;
        this.pourcentage = pourcentage;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Reduction(int idReduction, String code, String description, int pourcentage, LocalDate dateDebut, LocalDate dateFin) {
        this.idReduction = idReduction;
        this.code = code;
        this.description = description;
        this.pourcentage = pourcentage;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }


    // Getters
    public int getIdReduction() { return idReduction; }
    public String getCode() { return code; }
    public String getDescription() { return description; } // ✅ Ajout
    public int getPourcentage() { return pourcentage; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }

    // Setters
    public void setIdReduction(int idReduction) { this.idReduction = idReduction; }
    public void setCode(String code) { this.code = code; }
    public void setDescription(String description) { this.description = description; } // ✅ Ajout
    public void setPourcentage(int pourcentage) { this.pourcentage = pourcentage; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
}
