package org.sistemajava;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Prueba extends JPanel {

    private List<RowPanel> rowPanels = new ArrayList<>();

    public Prueba() {
        setLayout(new GridLayout(0, 1));
        JLabel charsetLabel = new JLabel("Número de Filas:");
        String[] charsets = {"1", "2", "3", "4", "5", "6", "7", "8"};
        JComboBox<String> charsetComboBox = new JComboBox<>(charsets);
        add(charsetLabel);
        add(charsetComboBox);
        JButton createTableButton = new JButton("Crear Tabla");
        createTableButton.addActionListener(e -> createTable());
        add(createTableButton);
    }

    private void createTable() {
        removeAll();
        int numRows = Integer.parseInt(((JComboBox<?>) getComponent(1)).getSelectedItem().toString());
        JPanel headerPanel = new JPanel(new GridLayout(1, 7));
        headerPanel.add(new JLabel("Nombre"));
        headerPanel.add(new JLabel("Tipo de Dato"));
        headerPanel.add(new JLabel("Longitud"));
        headerPanel.add(new JLabel("PK"));
        headerPanel.add(new JLabel("FK"));
        headerPanel.add(new JLabel("NULL"));

        add(headerPanel);
        rowPanels.clear();
        for (int i = 0; i < numRows; i++) {
            RowPanel rowPanel = new RowPanel();
            rowPanels.add(rowPanel);
            add(rowPanel);
        }
        revalidate();
        repaint();
    }

    class RowPanel extends JPanel {
        JTextField nameField = new JTextField(10);
        JComboBox<String> dataTypeComboBox = new JComboBox<>(new String[]{"Int", "String", "Date"});
        JTextField lengthField = new JTextField(5);
        JCheckBox pkCheckBox = new JCheckBox();
        JCheckBox fkCheckBox = new JCheckBox();
        JCheckBox nullCheckBox = new JCheckBox();

        public RowPanel() {
            setLayout(new GridLayout(1, 7));
            add(nameField);
            add(dataTypeComboBox);
            add(lengthField);
            add(pkCheckBox);
            add(fkCheckBox);
            add(nullCheckBox);
        }
    }

    public static void main(String[] args) {

    }
}
