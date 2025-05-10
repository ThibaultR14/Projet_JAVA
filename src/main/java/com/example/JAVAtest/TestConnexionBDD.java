package com.example.JAVAtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnexionBDD {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/booking";
        String user = "root";
        String password = "";

        try (Connection connexion = DriverManager.getConnection(url, user, password)) {
            if (connexion != null) {
                System.out.println("Connexion réussie");
            } else {
                System.out.println("Connexion échouée");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion : " + e.getMessage());
        }
    }
}
