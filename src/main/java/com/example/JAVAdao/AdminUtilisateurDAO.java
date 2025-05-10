package com.example.JAVAdao;

import com.example.JAVAmodel.AdminUtilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminUtilisateurDAO {
    public static class ConnexionBDD {

        private static final String URL = "jdbc:mysql://localhost:3306/booking";
        private static final String USER = "root";
        private static final String PASSWORD = "";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }
    public List<AdminUtilisateur> getAllUtilisateurs() {
        List<AdminUtilisateur> list = new ArrayList<>();
        String sql = "SELECT idUser, nom FROM Utilisateur";

        try (Connection conn = ConnexionBDD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(new AdminUtilisateur(rs.getInt("idUser"), rs.getString("nom")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
