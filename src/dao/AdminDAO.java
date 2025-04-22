// AdminDAO.java
package dao;

import model.Admin;
import utils.ConnexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    public Admin findByEmail(String email) {
        Admin admin = null;
        String sql = "SELECT u.id, u.nom, u.email, u.mot_de_passe " +
                "FROM utilisateur u INNER JOIN admin a ON u.id = a.id_admin " +
                "WHERE u.email = ?";

        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String motDePasse = rs.getString("mot_de_passe");

                admin = new Admin(id, nom, email, motDePasse);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche de l'admin : " + e.getMessage());
        }

        return admin;
    }
}
