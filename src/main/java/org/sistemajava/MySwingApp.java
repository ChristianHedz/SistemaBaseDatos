package org.sistemajava;

import org.sistemajava.utils.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.Statement;

public class MySwingApp {
    private ConexionBD conexionBD;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Ventana Principal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());

            JLabel nameLabel = new JLabel("Nombre Tabla:");
            JTextField nameTextField = new JTextField(20);

            JLabel charsetLabel = new JLabel("Filas:");
            String[] charsets = {"1", "2", "3", "4"};
            JComboBox<String> charsetComboBox = new JComboBox<>(charsets);

            JButton createButton = new JButton("Crear");
            createButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            createButton.addActionListener(e -> {
                String charset = (String) charsetComboBox.getSelectedItem();
                ConexionBD conexionBase = null;
                try {

                     conexionBase = new ConexionBD(nameTextField.getText(), "root", "", "localhost", "3306");
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
                try (Connection connection = conexionBase.obtenerConexion();

                     Statement statement = connection.createStatement()) {
                    String createDatabaseQuery = "CREATE DATABASE `" + nameTextField.getText() + "` CHARACTER SET 'utf8'";
                    statement.executeUpdate(createDatabaseQuery);
                    JOptionPane.showMessageDialog(null, "Base de datos creada con éxito", "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
                    createNewTableWindow(nameTextField.getText(), connection);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al crear la base de datos", "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            frame.add(nameLabel);
            frame.add(nameTextField);
            frame.add(charsetLabel);
            frame.add(charsetComboBox);
            frame.add(createButton);

            frame.setSize(300, 150);
            frame.setVisible(true);
        });
    }

    public static void createNewTableWindow(String dbName, Connection connection) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tabla de Filas");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JTable table = new JTable();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Nombre de la Fila");
            model.addColumn("Longitud");
            model.addColumn("Primary Key");
            model.addColumn("Auto Increment");
            table.setModel(model);

            JButton addRowButton = new JButton("Agregar Fila");
            addRowButton.addActionListener(e -> createNewRowWindow(model));

            frame.add(new JScrollPane(table), BorderLayout.CENTER);
            frame.add(addRowButton, BorderLayout.SOUTH);

            frame.setSize(400, 300);
            frame.setVisible(true);
        });
    }

    /**
     *
     */
    public static void createNewRowWindow(DefaultTableModel model) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Agregar Fila");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new FlowLayout());

            JLabel nameLabel = new JLabel("Nombre de la Fila:");
            JTextField nameTextField = new JTextField(15);

            JLabel lengthLabel = new JLabel("Longitud:");
            JTextField lengthTextField = new JTextField(5);
            JCheckBox primaryKeyCheckBox = new JCheckBox("Primary Key");
            JCheckBox autoIncrementCheckBox = new JCheckBox("Auto Increment");


            JButton addButton = new JButton("Agregar");


            addButton.addActionListener(e -> {
                String name = nameTextField.getText();
                String length = lengthTextField.getText();
                boolean primaryKey = primaryKeyCheckBox.isSelected();
                boolean autoIncrement = autoIncrementCheckBox.isSelected();
                model.addRow(new Object[]{name, length, primaryKey, autoIncrement});
                frame.dispose();
            });

            frame.add(nameLabel);
            frame.add(nameTextField);
            frame.add(lengthLabel);
            frame.add(lengthTextField);
            frame.add(primaryKeyCheckBox);
            frame.add(autoIncrementCheckBox);
            frame.add(addButton);

            frame.setSize(300, 150);
            frame.setVisible(true);
        });
    }
}
