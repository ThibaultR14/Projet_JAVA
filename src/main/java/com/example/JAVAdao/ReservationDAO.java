package com.example.JAVAdao;

import com.example.JAVAmodel.Reservation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservationDAO {

    public static boolean ajouterReservation(Reservation r) {
        String sql = """
            INSERT INTO Reservation (dateArrivee, dateDepart, nbAdulte, nbEnfant, idUser, idHebergement)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(r.getDateArrivee()));
            stmt.setDate(2, Date.valueOf(r.getDateDepart()));
            stmt.setInt(3, r.getNbAdulte());
            stmt.setInt(4, r.getNbEnfant());
            stmt.setInt(5, r.getIdUser());
            stmt.setInt(6, r.getIdHebergement());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
