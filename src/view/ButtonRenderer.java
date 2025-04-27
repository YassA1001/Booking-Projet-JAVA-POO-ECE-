package view;

import dao.ReservationDAO;
import model.Client;
import model.Reservation;
import utils.FactureGenerator;
import utils.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        setText((value == null) ? "Télécharger" : value.toString());
        return this;
    }

    public static class ReservationPanel extends JPanel {

        private Timer timer;
        private int xPos;
        private JFrame parentFrame;

        public ReservationPanel(JFrame parentFrame) {
            this.parentFrame = parentFrame;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(300, parentFrame.getHeight()));
            setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY)); // petite bordure gauche

            chargerReservations();
        }

        private void chargerReservations() {
            removeAll();

            Client client = SessionManager.getClient();
            if (client == null) {
                JLabel label = new JLabel("Aucun client connecté", CENTER);
                add(label);
                revalidate();
                repaint();
                return;
            }

            ReservationDAO dao = new ReservationDAO();
            java.util.List<Reservation> reservations = dao.findByClientId(client.getId());

            if (reservations.isEmpty()) {
                JLabel label = new JLabel("Aucune réservation.");
                label.setHorizontalAlignment(CENTER);
                add(label);
            } else {
                for (Reservation r : reservations) {
                    JPanel card = new JPanel(new BorderLayout());
                    card.setBackground(new Color(245, 245, 245));
                    card.setBorder(new EmptyBorder(10, 10, 10, 10));

                    JLabel info = new JLabel("<html><b>" + r.getHebergement().getNom() + "</b><br>" +
                            r.getDateArrivee() + " → " + r.getDateDepart() + "<br>" +
                            r.calculerTotal() + " €</html>");

                    JButton factureBtn = new JButton("Facture PDF");
                    factureBtn.addActionListener(e -> FactureGenerator.genererFacturePDF(r));

                    card.add(info, BorderLayout.CENTER);
                    card.add(factureBtn, BorderLayout.EAST);

                    add(card);
                    add(Box.createRigidArea(new Dimension(0, 10)));
                }
            }

            revalidate();
            repaint();
        }

        public void slideIn() {
            xPos = parentFrame.getWidth();
            setBounds(xPos, 0, getPreferredSize().width, parentFrame.getHeight());
            parentFrame.add(this);
            parentFrame.setComponentZOrder(this, 0);

            timer = new Timer(5, e -> {
                if (xPos > parentFrame.getWidth() - getPreferredSize().width) {
                    xPos -= 20; // vitesse
                    setLocation(xPos, 0);
                    parentFrame.repaint();
                } else {
                    timer.stop();
                }
            });
            timer.start();
        }
    }
}
