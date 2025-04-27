package view;

import model.Reservation;
import utils.FactureGenerator;
import javax.swing.DefaultCellEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ButtonEditor extends DefaultCellEditor {
    private final JButton button;
    private final List<Reservation> reservations;
    private int currentRow;

    public ButtonEditor(JCheckBox checkBox, List<Reservation> reservations) {
        super(checkBox);
        this.reservations = reservations;
        this.button = new JButton("Télécharger");

        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reservation r = reservations.get(currentRow);
                FactureGenerator.genererFacturePDF(r);
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        currentRow = row;
        button.setText((value == null) ? "Télécharger" : value.toString());
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "Télécharger";
    }
}
