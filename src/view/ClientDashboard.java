package view;

import dao.HebergementDAO;
import model.Hebergement;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.table.DefaultTableModel;


public class ClientDashboard extends JFrame {
    private ReservationPanel reservationPanel = new ReservationPanel(this);

    public ClientDashboard(String nomClient) {
        setTitle("Espace Client - Booking App");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Bienvenue, " + nomClient + " !", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        String[] columns = {"Nom", "Type", "Prix", "Adresse", "Capacité"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        HebergementDAO dao = new HebergementDAO();
        List<Hebergement> hebergements = dao.findAll();

        for (Hebergement h : hebergements) {
            tableModel.addRow(new Object[]{
                    h.getNom(),
                    h.getType(),
                    h.getPrixParNuit() + " €",
                    h.getAdresse(),
                    (h.getNbAdultes() + h.getNbEnfants()) + " pers / " + h.getNbChambres() + " ch"
            });
        }

        JButton reserverBtn = new JButton("Réserver l'hébergement sélectionné");
        reserverBtn.addActionListener((ActionEvent e) -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Hebergement h = hebergements.get(row);
                new ReservationForm(h).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un hébergement.");
            }
        });

        JButton voirReservationsBtn = new JButton("Mes Réservations");
        voirReservationsBtn.addActionListener(e -> afficherReservations());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.add(reserverBtn);
        bottomPanel.add(voirReservationsBtn);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void afficherReservations() {
        if (reservationPanel != null) {
            remove(reservationPanel);
        }
        add(reservationPanel, BorderLayout.EAST);
        revalidate();
        repaint();
        reservationPanel.slideIn(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientDashboard("Jean Dupont").setVisible(true));
    }
}
