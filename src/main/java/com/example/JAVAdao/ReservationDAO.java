package com.example.JAVAdao;

import com.example.JAVAmodel.Reservation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet; // ✅ manquant
import java.sql.SQLException;
import java.util.ArrayList; // ✅ manquant
import java.util.List;      // ✅ manquant

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

    public static List<Reservation> getReservationsByUserId(int idUser) {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM Reservation WHERE idUser = ?";

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUser);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Reservation(
                        rs.getDate("dateArrivee").toLocalDate(),
                        rs.getDate("dateDepart").toLocalDate(),
                        rs.getInt("nbAdulte"),
                        rs.getInt("nbEnfant"),
                        rs.getInt("idUser"),
                        rs.getInt("idHebergement")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static boolean supprimerReservation(int idHebergement, int idUser) {
        String sql = "DELETE FROM Reservation WHERE idHebergement = ? AND idUser = ?";

        try (Connection conn = connexionbdd.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idHebergement);
            stmt.setInt(2, idUser);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
