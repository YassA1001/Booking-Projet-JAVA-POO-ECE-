// ReservationForm.java
package view;

import dao.ReservationDAO;
import model.Client;
import model.Hebergement;
import model.Reservation;
import utils.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ReservationForm extends JFrame {

    public ReservationForm(Hebergement hebergement) {
        setTitle("Réservation - " + hebergement.getNom());
        setSize(400, 350);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel arriveeLabel = new JLabel("Date d'arrivée (YYYY-MM-DD):");
        JTextField arriveeField = new JTextField();

        JLabel departLabel = new JLabel("Date de départ (YYYY-MM-DD):");
        JTextField departField = new JTextField();

        JLabel adultesLabel = new JLabel("Nombre d'adultes:");
        JTextField adultesField = new JTextField("1");

        JLabel enfantsLabel = new JLabel("Nombre d'enfants:");
        JTextField enfantsField = new JTextField("0");

        JLabel message = new JLabel("");
        JButton confirmerBtn = new JButton("Confirmer la réservation");

        confirmerBtn.addActionListener(e -> {
            try {
                LocalDate arrivee = LocalDate.parse(arriveeField.getText());
                LocalDate depart = LocalDate.parse(departField.getText());
                int adultes = Integer.parseInt(adultesField.getText());
                int enfants = Integer.parseInt(enfantsField.getText());

                if (depart.isBefore(arrivee) || arrivee.isBefore(LocalDate.now())) {
                    message.setText("⚠️ Dates invalides.");
                    return;
                }

                Client client = SessionManager.getClient();
                if (client == null) {
                    message.setText("❌ Aucun client connecté.");
                    return;
                }

                Reservation reservation = new Reservation(0, client, hebergement, arrivee, depart, adultes, enfants);
                ReservationDAO dao = new ReservationDAO();
                boolean success = dao.enregistrerReservation(reservation);

                if (success) {
                    message.setText("✅ Réservation enregistrée avec succès !");
                } else {
                    message.setText("❌ Échec de l'enregistrement.");
                }
            } catch (DateTimeParseException | NumberFormatException ex) {
                message.setText("⚠️ Erreur dans les champs saisis.");
            }
        });

        panel.add(arriveeLabel);
        panel.add(arriveeField);
        panel.add(departLabel);
        panel.add(departField);
        panel.add(adultesLabel);
        panel.add(adultesField);
        panel.add(enfantsLabel);
        panel.add(enfantsField);
        panel.add(new JLabel());
        panel.add(confirmerBtn);
        panel.add(message);

        add(panel);
    }
}
