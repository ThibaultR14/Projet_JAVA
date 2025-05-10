package com.example.JAVAdao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connexionbdd {
    private static final String URL = "jdbc:mysql://localhost:3306/booking";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de connexion à la base de données", e);
        }
    }
}
