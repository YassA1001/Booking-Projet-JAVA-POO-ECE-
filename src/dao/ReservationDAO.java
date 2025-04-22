// Méthode à ajouter dans ReservationDAO.java

package dao;

import model.Client;
import model.Hebergement;
import model.Reservation;
import utils.ConnexionDB;
import dao.HebergementDAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    // ... méthode enregistrerReservation() déjà présente ...

    public List<Reservation> findByClientId(int clientId) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation WHERE id_client = ?";

        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();

            HebergementDAO hebergementDAO = new HebergementDAO();

            while (rs.next()) {
                int id = rs.getInt("id");
                int idHebergement = rs.getInt("id_hebergement");
                LocalDate dateArrivee = rs.getDate("date_arrivee").toLocalDate();
                LocalDate dateDepart = rs.getDate("date_depart").toLocalDate();
                int nbAdultes = rs.getInt("nb_adultes");
                int nbEnfants = rs.getInt("nb_enfants");

                Hebergement hebergement = hebergementDAO.findById(idHebergement);
                Client client = new Client(clientId, "", "", "", false); // données minimales nécessaires

                reservations.add(new Reservation(id, client, hebergement, dateArrivee, dateDepart, nbAdultes, nbEnfants));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservations;
    }
    public boolean enregistrerReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (id_client, id_hebergement, date_arrivee, date_depart, nb_adultes, nb_enfants) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservation.getClient().getId());
            stmt.setInt(2, reservation.getHebergement().getId());
            stmt.setDate(3, Date.valueOf(reservation.getDateArrivee()));
            stmt.setDate(4, Date.valueOf(reservation.getDateDepart()));
            stmt.setInt(5, reservation.getNbAdultes());
            stmt.setInt(6, reservation.getNbEnfants());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
