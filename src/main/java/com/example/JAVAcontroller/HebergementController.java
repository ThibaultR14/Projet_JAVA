package com.example.JAVAcontroller;

import com.example.JAVAdao.HebergementDAO;
import com.example.JAVAmodel.Hebergement;

import java.util.List;

public class HebergementController {

    private HebergementDAO hebergementDAO;

    // Constructeur
    public HebergementController() {
        this.hebergementDAO = new HebergementDAO();
    }

    // Méthode pour récupérer tous les hébergements
    public List<Hebergement> getAllHebergements() {
        return hebergementDAO.findAll();
    }

    // Méthode pour rechercher les hébergements selon des critères
    public List<Hebergement> searchHebergements(String ville, int prixMax, int nbEtoiles) {
        return hebergementDAO.findByCriteria(ville, prixMax, nbEtoiles);
    }

    // Méthode pour ajouter un nouvel hébergement
    public boolean addHebergement(Hebergement hebergement) {
        return hebergementDAO.insert(hebergement);
    }

    // Méthode pour supprimer un hébergement existant
    public boolean deleteHebergement(int idHebergement) {
        return hebergementDAO.delete(idHebergement);
    }
}
