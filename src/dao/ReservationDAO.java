// ReservationDAO.java
package dao;

import model.Hebergement;
import model.Reservation;
import utils.ConnexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    public List<Reservation> findByClientId(int clientId) {
        List<Reservation> reservations = new ArrayList<>();

        String sql = """
            SELECT r.id, r.date_arrivee, r.date_depart, r.nb_adultes, r.nb_enfants,
                   h.id AS hebergement_id, h.nom AS hebergement_nom, h.type, h.prix_par_nuit, 
                   h.adresse, h.nb_chambres, h.nb_adultes, h.nb_enfants, h.image
            FROM reservation r
            JOIN hebergement h ON r.id_hebergement = h.id
            WHERE r.id_client = ?
        """;

        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Hebergement hebergement = new Hebergement(
                            rs.getInt("hebergement_id"),
                            rs.getString("hebergement_nom"),
                            rs.getString("type"),
                            rs.getDouble("prix_par_nuit"),
                            rs.getString("adresse"),
                            rs.getInt("nb_chambres"),
                            rs.getInt("nb_adultes"),
                            rs.getInt("nb_enfants"),
                            rs.getString("image")
                    );

                    Reservation reservation = new Reservation(
                            rs.getInt("id"),
                            null, // on met null pour le client
                            hebergement,
                            rs.getDate("date_arrivee").toLocalDate(),
                            rs.getDate("date_depart").toLocalDate(),
                            rs.getInt("nb_adultes"),
                            rs.getInt("nb_enfants")
                    );

                    reservations.add(reservation);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservations;
    }

    public boolean enregistrerReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (id_client, id_hebergement, date_arrivee, date_depart, nb_adultes, nb_enfants) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservation.getClient().getId());
            stmt.setInt(2, reservation.getHebergement().getId());
            stmt.setDate(3, java.sql.Date.valueOf(reservation.getDateArrivee()));
            stmt.setDate(4, java.sql.Date.valueOf(reservation.getDateDepart()));
            stmt.setInt(5, reservation.getNbAdultes());
            stmt.setInt(6, reservation.getNbEnfants());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
