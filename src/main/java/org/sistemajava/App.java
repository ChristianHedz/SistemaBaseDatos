package org.sistemajava;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test de Conexión a Base de Datos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        JTextField urlField = new JTextField(31);
        JTextField userField = new JTextField(31);
        JPasswordField passwordField = new JPasswordField(31);
        JButton testButton = new JButton("Test Conexión");
        JButton testButton2 = new JButton("Realizar Conexion");
        testButton2.setPreferredSize(new Dimension(222, 30));

        panel.add(createFieldPanel("Nombre de la Base de Datos:", urlField));
        panel.add(createFieldPanel("Usuario:", userField));
        panel.add(createFieldPanel("Contraseña:", passwordField));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(testButton);
        buttonPanel.add(testButton2);
        panel.add(buttonPanel);
        frame.add(panel);

        testButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                testConexion(frame, urlField, userField, passwordField);
            }
        });

        testButton2.addActionListener(e -> {
            testConexion(frame, urlField, userField, passwordField);
            frame.dispose();
        });

        frame.setVisible(true);
    }

    private static JPanel createFieldPanel(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        panel.add(label);
        panel.add(field);
        return panel;
    }

    public static void testConexion(JFrame frame, JTextField urlField, JTextField userField, JPasswordField passwordField) {
        String nombreBD = urlField.getText();
        String url=  "jdbc:mysql://localhost:3306/ " + nombreBD + "?serverTimezone=America/Mexico_City";
        String usuario = userField.getText();
        String contraseña = new String(passwordField.getPassword());

        Connection conexion = ConexionBD.obtenerConexion(url, usuario, contraseña);
        if (conexion != null) {
            JOptionPane.showMessageDialog(frame, "Conexión exitosa.");
            ConexionBD.cerrarConexion();
        }
    }

}
