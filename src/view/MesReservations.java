package view;

import dao.ReservationDAO;
import model.Client;
import model.Reservation;
import utils.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.List;

public class MesReservations extends JFrame {

    public MesReservations() {
        setTitle("Mes Réservations");
        setSize(800, 400);
        setLocationRelativeTo(null);

        Client client = SessionManager.getClient();
        if (client == null) {
            JOptionPane.showMessageDialog(this, "Aucun client connecté.");
            dispose();
            return;
        }

        ReservationDAO dao = new ReservationDAO();
        List<Reservation> reservations = dao.findByClientId(client.getId());

        String[] colonnes = {"Hébergement", "Arrivée", "Départ", "Adultes", "Enfants", "Total €", "Facture"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        for (Reservation r : reservations) {
            double total = r.calculerTotal();
            model.addRow(new Object[] {
                    r.getHebergement().getNom(),
                    r.getDateArrivee(),
                    r.getDateDepart(),
                    r.getNbAdultes(),
                    r.getNbEnfants(),
                    total,
                    "Télécharger"
            });
        }

        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Seule la colonne "Facture" est éditable
            }
        };

        TableColumn factureColumn = table.getColumn("Facture");
        factureColumn.setCellRenderer(new ButtonRenderer());
        factureColumn.setCellEditor((TableCellEditor) new ButtonEditor(new JCheckBox(), reservations));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }
}
