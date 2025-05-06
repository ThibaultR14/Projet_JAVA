package com.example.projet_java;

import com.example.JAVAmodel.Utilisateur;

public class Session {
    private static Utilisateur utilisateurActif;

    public static void setUtilisateur(Utilisateur u) {
        utilisateurActif = u;
    }

    public static Utilisateur getUtilisateur() {
        return utilisateurActif;
    }

    public static boolean estConnecte() {
        return utilisateurActif != null;
    }

    public static void deconnexion() {
        utilisateurActif = null;
    }

}
