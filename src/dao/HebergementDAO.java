// HebergementDAO.java
package dao;

import model.Hebergement;
import utils.ConnexionDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HebergementDAO {

    public List<Hebergement> findAll() {
        List<Hebergement> list = new ArrayList<>();
        String sql = "SELECT * FROM hebergement";

        try (Connection conn = ConnexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Hebergement h = new Hebergement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getDouble("prix_par_nuit"),
                        rs.getString("adresse"),
                        rs.getInt("nb_chambres"),
                        rs.getInt("nb_adultes"),
                        rs.getInt("nb_enfants"),
                        rs.getString("image")
                );
                list.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Hebergement findById(int id) {
        String sql = "SELECT * FROM hebergement WHERE id = ?";
        Hebergement h = null;

        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                h = new Hebergement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getDouble("prix_par_nuit"),
                        rs.getString("adresse"),
                        rs.getInt("nb_chambres"),
                        rs.getInt("nb_adultes"),
                        rs.getInt("nb_enfants"),
                        rs.getString("image")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return h;
    }

    public boolean ajouter(Hebergement h) {
        String sql = "INSERT INTO hebergement (nom, type, description, prix_par_nuit, adresse, nb_chambres, nb_adultes, nb_enfants, image) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, h.getNom());
            stmt.setString(2, h.getType());
            stmt.setString(3, h.getDescription());
            stmt.setDouble(4, h.getPrixParNuit());
            stmt.setString(5, h.getAdresse());
            stmt.setInt(6, h.getNbChambres());
            stmt.setInt(7, h.getNbAdultes());
            stmt.setInt(8, h.getNbEnfants());
            stmt.setString(9, h.getImage());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM hebergement WHERE id = ?";

        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
