// ProfilForm.java
package view;

import dao.ClientDAO;
import model.Client;
import utils.SessionManager;

import javax.swing.*;
import java.awt.*;

public class ProfilForm extends JFrame {

    private JTextField nomField;
    private JTextField emailField;
    private JPasswordField mdpField;

    public ProfilForm() {
        setTitle("Profil");
        setSize(400, 300);
        setLocationRelativeTo(null);

        Client client = SessionManager.getClient();

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nomLabel = new JLabel("Nom:");
        nomField = new JTextField(client.getNom());

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(client.getEmail());

        JLabel mdpLabel = new JLabel("Mot de passe:");
        mdpField = new JPasswordField();

        JLabel message = new JLabel("");

        JButton saveBtn = new JButton("Enregistrer");
        saveBtn.addActionListener(e -> {
            String newNom = nomField.getText();
            String newEmail = emailField.getText();
            String newMdp = new String(mdpField.getPassword());

            ClientDAO dao = new ClientDAO();
            boolean success = dao.updateClient(client.getId(), newNom, newEmail, newMdp);

            if (success) {
                message.setText("✅ Infos mises à jour !");
                client.setNom(newNom);
                client.setEmail(newEmail);
                SessionManager.setClient(client);
            } else {
                message.setText("❌ Erreur lors de la mise à jour.");
            }
        });

        panel.add(nomLabel);
        panel.add(nomField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(mdpLabel);
        panel.add(mdpField);
        panel.add(new JLabel());
        panel.add(saveBtn);
        panel.add(message);

        add(panel);
    }
}
