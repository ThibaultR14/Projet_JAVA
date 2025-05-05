package com.example.JAVAcontroller;

import com.example.JAVAdao.HebergementDAO;
import com.example.JAVAmodel.Hebergement;

import java.time.LocalDate;
import java.util.List;

public class HebergementController {

    // Méthode pour récupérer tous les hébergements
    public List<Hebergement> getAllHebergements() {
        return HebergementDAO.findAll();
    }

    // Méthode pour rechercher les hébergements selon des critères
    public List<Hebergement> searchHebergements(String ville, int nbAdultes, int nbEnfants, LocalDate dateArrivee, LocalDate dateDepart) {
        return HebergementDAO.findByCriteria(ville, nbAdultes, nbEnfants, dateArrivee, dateDepart);
    }

    // Méthode pour ajouter un nouvel hébergement
    public boolean addHebergement(Hebergement hebergement) {
        return HebergementDAO.insert(hebergement);
    }

    // Méthode pour supprimer un hébergement existant
    public boolean deleteHebergement(int idHebergement) {
        return HebergementDAO.delete(idHebergement);
    }
}
