// LoginController.java
package controller;

import dao.AdminDAO;
import dao.ClientDAO;
import model.Admin;
import model.Client;
import utils.SessionManager;
import view.AdminDashboard;
import view.ClientDashboard;

import javax.swing.*;

public class LoginController {

    public static void handleLogin(String email, String password, JLabel messageLabel, JFrame frame) {
        ClientDAO clientDAO = new ClientDAO();
        AdminDAO adminDAO = new AdminDAO();

        Client client = clientDAO.findByEmail(email);
        if (client != null && client.getMotDePasse().equals(password)) {
            SessionManager.setClient(client);
            messageLabel.setText("Bienvenue, " + client.getNom() + " (client)");
            frame.dispose();
            SwingUtilities.invokeLater(() -> new ClientDashboard(client.getNom()).setVisible(true));
            return;
        }

        Admin admin = adminDAO.findByEmail(email);
        if (admin != null && admin.getMotDePasse().equals(password)) {
            messageLabel.setText("Bienvenue, " + admin.getNom() + " (admin)");
            frame.dispose();
            SwingUtilities.invokeLater(() -> new AdminDashboard(admin.getNom()).setVisible(true));
            return;
        }

        messageLabel.setText("Identifiants incorrects.");
    }
}