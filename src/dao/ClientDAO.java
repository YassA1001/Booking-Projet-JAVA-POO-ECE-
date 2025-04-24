package dao;

import model.Client;
import utils.ConnexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDAO {

    public Client findByEmail(String email) {
        Client client = null;
        String sql = "SELECT u.id, u.nom, u.email, u.mot_de_passe, u.type, c.est_ancien " +
                "FROM utilisateur u INNER JOIN client c ON u.id = c.id_client " +
                "WHERE u.email = ?";

        try (Connection conn = ConnexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String emailResult = rs.getString("email");
                String motDePasse = rs.getString("mot_de_passe");
                String type = rs.getString("type");
                boolean estAncien = rs.getBoolean("est_ancien");

                client = new Client(id, nom, emailResult, motDePasse, type, estAncien);
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du client : " + e.getMessage());
        }

        return client;
    }
}
