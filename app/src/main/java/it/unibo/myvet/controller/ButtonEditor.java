package it.unibo.myvet.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;
    private JTable table;
    private int row;
    @SuppressWarnings("unused")
    private int column;

    public ButtonEditor(JCheckBox checkBox, JTable table) {
        super(checkBox);
        this.table = table;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        this.row = row;
        this.column = column;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            // Azione da eseguire quando si preme il bottone
            String vetName = table.getValueAt(row, 0) + " " + table.getValueAt(row, 1);
            JOptionPane.showMessageDialog(button, "Hai aggiunto " + vetName + " ai tuoi preferiti!");
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
