package com.example.JAVAtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnexionBDD {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/booking"; // Mets bien le nom de ta base ici
        String user = "root"; // Ou ton user MySQL
        String password = ""; // Ton mot de passe MySQL

        try (Connection connexion = DriverManager.getConnection(url, user, password)) {
            if (connexion != null) {
                System.out.println("✅ Connexion à la base de données réussie !");
            } else {
                System.out.println("❌ Connexion échouée...");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
        }
    }
}
