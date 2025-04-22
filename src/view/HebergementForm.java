// HebergementForm.java
package view;

import dao.HebergementDAO;
import model.Hebergement;

import javax.swing.*;
import java.awt.*;

public class HebergementForm extends JFrame {

    public HebergementForm(Runnable onSuccess) {
        setTitle("Ajouter un hébergement");
        setSize(400, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nomField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField descField = new JTextField();
        JTextField prixField = new JTextField();
        JTextField adresseField = new JTextField();
        JTextField chambresField = new JTextField();
        JTextField adultesField = new JTextField();
        JTextField enfantsField = new JTextField();

        JLabel message = new JLabel();
        JButton ajouterBtn = new JButton("Ajouter");

        ajouterBtn.addActionListener(e -> {
            try {
                Hebergement h = new Hebergement(
                        0,
                        nomField.getText(),
                        typeField.getText(),
                        descField.getText(),
                        Double.parseDouble(prixField.getText()),
                        adresseField.getText(),
                        Integer.parseInt(chambresField.getText()),
                        Integer.parseInt(adultesField.getText()),
                        Integer.parseInt(enfantsField.getText())
                );

                HebergementDAO dao = new HebergementDAO();
                boolean success = dao.addHebergement(h);

                if (success) {
                    message.setText("✅ Ajout réussi !");
                    onSuccess.run();
                    dispose();
                } else {
                    message.setText("❌ Échec de l'ajout.");
                }
            } catch (Exception ex) {
                message.setText("⚠️ Erreur dans les champs saisis.");
            }
        });

        panel.add(new JLabel("Nom")); panel.add(nomField);
        panel.add(new JLabel("Type")); panel.add(typeField);
        panel.add(new JLabel("Description")); panel.add(descField);
        panel.add(new JLabel("Prix / nuit")); panel.add(prixField);
        panel.add(new JLabel("Adresse")); panel.add(adresseField);
        panel.add(new JLabel("Nb chambres")); panel.add(chambresField);
        panel.add(new JLabel("Nb adultes")); panel.add(adultesField);
        panel.add(new JLabel("Nb enfants")); panel.add(enfantsField);
        panel.add(ajouterBtn); panel.add(message);

        add(panel);
    }
}
