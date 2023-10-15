package org.sistemajava;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazSplitPane extends JFrame {
    private JSplitPane splitPane;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton button1;
    private JButton button2;
    private JLabel imageLabel;

    public InterfazSplitPane() {
        setTitle("Ejemplo de JSplitPane");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Crear botones en la parte izquierda
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(5, 1)); // Puedes ajustar el diseño según tus necesidades

        button1 = new JButton("Botón 1");
        button2 = new JButton("Botón 2");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para el botón 1
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para el botón 2
            }
        });

        leftPanel.add(button1);
        leftPanel.add(button2);

        // Crear el JLabel en la parte derecha para mostrar la imagen
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("src/main/resources/images/logoBD.jpeg"); // Cambia la ruta a tu imagen
        //hacer mas grande la imagen
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(300, 300,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back

        imageLabel.setIcon(imageIcon);
        rightPanel.add(imageLabel, BorderLayout.CENTER);
        // Centrar la imagen
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        //

        // Crear el JSplitPane y configurarlo
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.2); // Puedes ajustar la división inicial

        add(splitPane);

        // Mostrar la ventana
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfazSplitPane();
        });
    }
}
