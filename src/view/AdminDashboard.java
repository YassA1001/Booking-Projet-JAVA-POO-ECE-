// AdminDashboard.java
package view;

import dao.HebergementDAO;
import model.Hebergement;
import utils.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {

    private DefaultTableModel tableModel;
    private JTable table;
    private List<Hebergement> hebergements;
    private HebergementDAO dao;

    public AdminDashboard(String nomAdmin) {
        setTitle("Espace Admin - Booking App");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dao = new HebergementDAO();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Bienvenue, " + nomAdmin + " (admin)", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        String[] columns = {"Nom", "Type", "Prix", "Adresse", "Capacité"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton addBtn = new JButton("Ajouter un hébergement");
        JButton editBtn = new JButton("Modifier");
        JButton deleteBtn = new JButton("Supprimer");
        JButton logoutBtn = new JButton("Se déconnecter");

        addBtn.addActionListener(e -> new HebergementForm(this::loadHebergements).setVisible(true));
        editBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Modification (à venir)"));
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                Hebergement selected = hebergements.get(selectedRow);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Supprimer l'hébergement : " + selected.getNom() + " ?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    boolean deleted = dao.deleteById(selected.getId());
                    if (deleted) {
                        JOptionPane.showMessageDialog(this, "✅ Hébergement supprimé.");
                        loadHebergements();
                    } else {
                        JOptionPane.showMessageDialog(this, "❌ Échec de la suppression.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un hébergement.");
            }
        });

        logoutBtn.addActionListener(e -> {
            SessionManager.clearSession();
            dispose();
            new LoginForm().setVisible(true);
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(logoutBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        add(panel);
        loadHebergements();
    }

    private void loadHebergements() {
        hebergements = dao.findAll();
        tableModel.setRowCount(0);
        for (Hebergement h : hebergements) {
            tableModel.addRow(new Object[]{
                    h.getNom(),
                    h.getType(),
                    h.getPrixParNuit() + " €",
                    h.getAdresse(),
                    (h.getNbAdultes() + h.getNbEnfants()) + " pers / " + h.getNbChambres() + " ch"
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard("Admin Principal").setVisible(true));
    }
}
