// ReservationPanel.java
package view;

import dao.ReservationDAO;
import model.Reservation;
import utils.FactureGenerator;
import utils.SessionManager;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReservationPanel extends JPanel {

    private Timer timer;
    private int xPos;

    public ReservationPanel(JFrame parentFrame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(300, parentFrame.getHeight()));
        setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));

        chargerReservations();
    }

    public void slideIn(JFrame parentFrame) {
        xPos = parentFrame.getWidth();
        setLocation(xPos, 0);

        Timer timer = new Timer(5, e -> {
            xPos -= 10;
            if (xPos <= parentFrame.getWidth() - getWidth()) {
                ((Timer) e.getSource()).stop();
            }
            setLocation(xPos, 0);
        });
        timer.start();
    }

    private void chargerReservations() {
        removeAll();

        Client client = SessionManager.getClient();
        if (client == null) {
            JLabel label = new JLabel("Aucun client connecté", SwingConstants.CENTER);
            add(label, BorderLayout.CENTER);
            return;
        }

        ReservationDAO dao = new ReservationDAO();
        List<Reservation> reservations = dao.findByClientId(client.getId());

        if (reservations.isEmpty()) {
            JLabel label = new JLabel("Aucune réservation.", SwingConstants.CENTER);
            add(label);
        } else {
            for (Reservation r : reservations) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBackground(new Color(230, 240, 255));
                card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel info = new JLabel("<html><b>" + r.getHebergement().getNom() + "</b><br>"
                        + r.getDateArrivee() + " → " + r.getDateDepart() + "<br>"
                        + r.calculerTotal() + " €</html>");

                JButton factureBtn = new JButton("Télécharger PDF");
                factureBtn.addActionListener(e -> {
                    FactureGenerator.genererFacturePDF(r);
                });

                card.add(info, BorderLayout.CENTER);
                card.add(factureBtn, BorderLayout.EAST);

                add(card);
                add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        revalidate();
        repaint();
    }
}
