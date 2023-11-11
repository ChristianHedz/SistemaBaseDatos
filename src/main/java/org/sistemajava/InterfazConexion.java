package org.sistemajava;

import lombok.NoArgsConstructor;
import org.sistemajava.utils.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

@NoArgsConstructor
public class InterfazConexion extends JPanel{
    private JSplitPane splitPane;
    private JPanel newLeftPanel;
    private JTextField usuarioField;
    private JTextField nombreField;
    private JPasswordField passwordField;
    private JTextField hostField;
    private JTextField puertoField;
    private JFrame newFrame;
    private JButton testButton = new JButton("Test✅");
    private Connection connection;
    private ConexionBD conexionBD;

    public InterfazConexion(JSplitPane splitPane, JPanel newLeftPanel){
        this.splitPane = splitPane;
        this.newLeftPanel = newLeftPanel;
    }



    public void interfazConexion(){


        JPanel newRightPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIconFondo = new ImageIcon("src/main/resources/images/fondoBlackSQL.png");
                Image image = imageIconFondo.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        newRightPanel.setLayout(new GridBagLayout());
        newRightPanel.setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usuarioLabel = new JLabel("USUARIO: ");
        //cambiar color de letra
        usuarioLabel.setForeground(Color.WHITE);
        Font font = new Font("Roboto", Font.BOLD, 17);
        usuarioLabel.setFont(font);
        newRightPanel.add(usuarioLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        usuarioField = new JTextField(20);
        usuarioField.setText("root");
        newRightPanel.add(usuarioField, gbc);

        nombreField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel contrasenaLabel = new JLabel("CONTRASEÑA: ");
        contrasenaLabel.setForeground(Color.WHITE);
        contrasenaLabel.setFont(font);
        newRightPanel.add(contrasenaLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        passwordField = new JPasswordField(20);
        char[] password = passwordField.getPassword();
        String contrasena = new String(password);
        newRightPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel hostLabel = new JLabel("HOST: ");
        hostLabel.setForeground(Color.WHITE);
        hostLabel.setFont(font);
        newRightPanel.add(hostLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        hostField = new JTextField(20);
        hostField.setText("localhost");
        newRightPanel.add(hostField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel puertoLabel = new JLabel("PUERTO: ");
        puertoLabel.setForeground(Color.WHITE);
        puertoLabel.setFont(font);
        newRightPanel.add(puertoLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        puertoField = new JTextField(20);
        puertoField.setText("3306");
        newRightPanel.add(puertoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton conectarButton = new JButton("Conectar");
        ImageIcon imageIcon = new ImageIcon("src/main/resources/images/testBD.png");
        CreateSplitButtons.personalizarBoton(conectarButton,imageIcon);


        ImageIcon imageIcon2 = new ImageIcon("src/main/resources/images/testBD.png");
        CreateSplitButtons.personalizarBoton(testButton,imageIcon2);

        buttonPanel.add(testButton);
        buttonPanel.add(conectarButton);
        newRightPanel.add(buttonPanel, gbc);

        JScrollPane scrollPane = new JScrollPane(newRightPanel);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        JSplitPane newSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, newLeftPanel, scrollPane);
        newSplitPane.setResizeWeight(0.2);

        if (splitPane != null) {
            remove(splitPane);
        }
        add(newSplitPane);
        splitPane = newSplitPane;

        newFrame = new JFrame("INTERFAZ DE CONEXIÓN A BASE DE DATOS");
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newFrame.getContentPane().add(newSplitPane);
        newFrame.setSize(1200, 720);
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);
        newFrame.setBackground(Color.BLACK);
        revalidate();

        conectarButton.addActionListener(e -> {
            InicioBD inicioBD = new InicioBD();
            conexionBD = new ConexionBD(usuarioField.getText(), contrasena, hostField.getText(), puertoField.getText());
            Connection connection = conexionBD.obtenerConexion();
            if (connection != null) {
                JOptionPane.showMessageDialog(newFrame, "Conexión exitosa.");
                inicioBD.createNewSplitPane2(newSplitPane);
                newFrame.dispose();
            }
        });

        testButton.addActionListener(e -> {
            ConexionBD conexionBD = new ConexionBD(usuarioField.getText(), contrasena, hostField.getText(), puertoField.getText());
            Connection connection = conexionBD.obtenerConexion();
            if (connection != null) {
                JOptionPane.showMessageDialog(newFrame,"La conexion exitosa","Test Exitoso✅", JOptionPane.INFORMATION_MESSAGE);
            }

        });

    }



    public void cerrarVentana() {
        newFrame.dispose();
    }

    public void limpiarCampos() {
        usuarioField.setText("");
        nombreField.setText("");
        passwordField.setText("");
        hostField.setText("");
        puertoField.setText("");
    }
}
