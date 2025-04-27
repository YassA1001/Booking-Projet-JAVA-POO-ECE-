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

    // ✅ Nouvelle méthode à ajouter :
    public void ensureClientExists(int userId) {
        String checkSql = "SELECT COUNT(*) FROM client WHERE id_client = ?";
        String insertSql = "INSERT INTO client (id_client, est_ancien) VALUES (?, false)";

        try (Connection conn = ConnexionDB.getConnection()) {
            // Vérification
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, userId);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();

            if (rs.getInt(1) == 0) {
                // Insertion si absent
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, userId);
                insertStmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification/ajout du client : " + e.getMessage());
        }
    }

    public boolean updateClient(int clientId, String newNom, String newEmail, String newMdp) {
            String sql = "UPDATE utilisateur SET nom = ?, email = ?, mot_de_passe = ? WHERE id = ?";

            try (Connection conn = ConnexionDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, newNom);
                stmt.setString(2, newEmail);
                stmt.setString(3, newMdp);
                stmt.setInt(4, clientId);

                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

    public static class updateclient {
        public updateclient(int id, String newNom, String newEmail, String newMdp) {
        }
    }
}

