package com.example.JAVAcontroller;

import com.example.JAVAdao.HebergementDAO;
import com.example.JAVAmodel.Hebergement;

import java.time.LocalDate;
import java.util.List;

public class HebergementController {

    public List<Hebergement> getAllHebergements() {
        return HebergementDAO.findAll();
    }

    public List<Hebergement> searchHebergements(String ville, int nbAdultes, int nbEnfants, LocalDate dateArrivee, LocalDate dateDepart) {
        return HebergementDAO.findByCriteria(ville, nbAdultes, nbEnfants, dateArrivee, dateDepart);
    }

    public boolean addHebergement(Hebergement hebergement) {
        return HebergementDAO.insert(hebergement);
    }

    public boolean deleteHebergement(int idHebergement) {
        return HebergementDAO.delete(idHebergement);
    }
}
