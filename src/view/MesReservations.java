// MesReservations.java
package view;

import dao.ReservationDAO;
import model.Client;
import model.Reservation;
import utils.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MesReservations extends JFrame {

    public MesReservations() {
        setTitle("Mes Réservations");
        setSize(700, 400);
        setLocationRelativeTo(null);

        Client client = SessionManager.getClient();
        if (client == null) {
            JOptionPane.showMessageDialog(this, "Aucun client connecté.");
            dispose();
            return;
        }

        ReservationDAO dao = new ReservationDAO();
        List<Reservation> reservations = dao.findByClientId(client.getId());

        String[] colonnes = {"Hébergement", "Arrivée", "Départ", "Adultes", "Enfants", "Total €"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        for (Reservation r : reservations) {
            double total = r.calculerTotal();
            model.addRow(new Object[]{
                    r.getHebergement().getNom(),
                    r.getDateArrivee(),
                    r.getDateDepart(),
                    r.getNbAdultes(),
                    r.getNbEnfants(),
                    total
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MesReservations().setVisible(true));
    }
}
