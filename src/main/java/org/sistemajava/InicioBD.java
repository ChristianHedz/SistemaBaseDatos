package org.sistemajava;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InicioBD extends JFrame {

    private JSplitPane splitPane;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton button1;
    private JButton button2;
    private JLabel imageLabel;

    public InicioBD() {

        setTitle("Bienvenido a mi entorno grafico de base de datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(7, 1));

        button1 = new JButton("Conectar");
        ImageIcon imageIconi = new ImageIcon("src/main/resources/images/logo.png");
        personalizarBoton(button1,imageIconi);

        button2 = new JButton("Botón 2");
        button1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        leftPanel.add(button1);
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        imageLabel = new JLabel();
        rightPanel.add(new JLabel("Bienvenido a mi aplicación de Java Swing"));
        ImageIcon imageIcon = new ImageIcon("src/main/resources/images/logo.png");
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(300, 300,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        imageLabel.setIcon(imageIcon);
        rightPanel.add(imageLabel, BorderLayout.CENTER);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.2);
        add(splitPane);
        setLocationRelativeTo(null);
        setVisible(true);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewSplitPane();
            }
        });
    }

    private void createNewSplitPane() {
        JPanel newLeftPanel = new JPanel();
        JButton button3 = new JButton("TEST");
        ImageIcon imageIcon = new ImageIcon("src/main/resources/images/testBD.png");
        personalizarBoton(button3,imageIcon);
        JButton button4 = new JButton("");
        button4.setBackground(Color.decode("#F2F2F2"));
        JButton button5 = new JButton("LIMPIAR");
        ImageIcon imageIcon2 = new ImageIcon("src/main/resources/images/limpiar.png");
        personalizarBoton(button5,imageIcon2);
        newLeftPanel.add(button3);
        newLeftPanel.add(button4);
        newLeftPanel.add(button5);
        newLeftPanel.setLayout(new GridLayout(7, 1));
        newLeftPanel.setPreferredSize(new Dimension(20, 20));

        JPanel newRightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        newRightPanel.add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField usuarioField = new JTextField(20);
        newRightPanel.add(usuarioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        newRightPanel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JPasswordField passwordField = new JPasswordField(20);
        newRightPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        newRightPanel.add(new JLabel("Host:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JTextField hostField = new JTextField(20);
        newRightPanel.add(hostField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        newRightPanel.add(new JLabel("Puerto:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JTextField puertoField = new JTextField(20);
        newRightPanel.add(puertoField, gbc);

        // Agregar botones uno al lado del otro
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton conectarButton = new JButton("Conectar");
        JButton cancelarButton = new JButton("Test");
        buttonPanel.add(cancelarButton);
        buttonPanel.add(conectarButton);
        newRightPanel.add(buttonPanel, gbc);

        JSplitPane newSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, newLeftPanel, newRightPanel);
        newSplitPane.setResizeWeight(0.2);

        conectarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewSplitPane2(newSplitPane);
            }
        });

        remove(splitPane);
        add(newSplitPane);
        splitPane = newSplitPane;

        revalidate();
    }

    private void createNewSplitPane2(JSplitPane newSplitPane) {
            JPanel newLeftPanel2 = createSplitButton();

            JPanel newRightPanel2 = new JPanel();
            newRightPanel2.setLayout(new GridBagLayout());

            JLabel nameLabel = new JLabel("Nombre Base de Datos:");
            JTextField nameTextField = new JTextField(20);

            JLabel charsetLabel = new JLabel("Charset:");
            String[] charsets = {"UTF-8", "ISO-8859-1", "UTF-16"};
            JComboBox<String> charsetComboBox = new JComboBox<>(charsets);

            JButton createButton = new JButton("Crear");
            createButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;

            newRightPanel2.add(new JLabel("Crear Base de Datos:"), gbc);

            gbc.gridy++;
            newRightPanel2.add(nameLabel, gbc);
            gbc.gridy++;
            newRightPanel2.add(nameTextField, gbc);
            gbc.gridy++;
            newRightPanel2.add(charsetLabel, gbc);
            gbc.gridy++;
            newRightPanel2.add(charsetComboBox, gbc);
            gbc.gridy++;
            gbc.anchor = GridBagConstraints.CENTER;
            newRightPanel2.add(createButton, gbc);

            JSplitPane newSplitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, newLeftPanel2, newRightPanel2);
            newSplitPane2.setResizeWeight(0.2);

            createButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createNewSplitPane3(newSplitPane2);
                }
            });

            remove(newSplitPane);
            add(newSplitPane2);
            newSplitPane = newSplitPane2;
            revalidate();
        }


    private void createNewSplitPane3(JSplitPane newSplitPane) {
        JPanel newLeftPanel2 = createSplitButton();
        JPanel newRightPanel2 = new JPanel();
        newRightPanel2.setLayout(new GridBagLayout());

        JLabel nameLabel = new JLabel("Nombre Tabla:");
        JTextField nameTextField = new JTextField(20);

        JLabel charsetLabel = new JLabel("Columnas:");
        String[] charsets = {"1", "2", "3", "4", "5", "6", "7", "8"};
        JComboBox<String> charsetComboBox = new JComboBox<>(charsets);

        JButton createButton = new JButton("Crear");
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);



        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        newRightPanel2.add(new JLabel("Crear tabla en la base de datos:"), gbc);

        gbc.gridy++;
        newRightPanel2.add(nameLabel, gbc);
        gbc.gridy++;
        newRightPanel2.add(nameTextField, gbc);
        gbc.gridy++;
        newRightPanel2.add(charsetLabel, gbc);
        gbc.gridy++;
        newRightPanel2.add(charsetComboBox, gbc);
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        newRightPanel2.add(createButton, gbc);

        JSplitPane newSplitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, newLeftPanel2, newRightPanel2);
        newSplitPane2.setResizeWeight(0.2);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tableName = nameTextField.getText();
                int columnCount = Integer.parseInt((String) charsetComboBox.getSelectedItem());

                newRightPanel2.removeAll();

                JPanel mainPanel = new JPanel(new GridLayout(0, 2)); // Una fila, dos columnas
                mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                for (int i = 0; i < columnCount; i++) {
                    JTextField columnNameField = new JTextField(20);
                    JTextField columnNumberField = new JTextField(20);
                    JComboBox<String> dataTypeComboBox = new JComboBox<>(new String[]{"Texto", "Número", "Fecha"});
                    JTextField lengthTextField = new JTextField(10);
                    JCheckBox primaryKeyCheckBox = new JCheckBox("PK");
                    JCheckBox foreignKeyCheckBox = new JCheckBox("FK");
                    JCheckBox allowNullCheckBox = new JCheckBox("NULL");

                    mainPanel.add(new JLabel("Columna " + (i + 1) + ": Nombre:"));
                    mainPanel.add(columnNameField);
                    mainPanel.add(new JLabel("Tipo de Dato:"));
                    mainPanel.add(dataTypeComboBox);
                    mainPanel.add(new JLabel("Longitud:"));
                    mainPanel.add(lengthTextField);
                    mainPanel.add(new JLabel("PK:"));
                    mainPanel.add(primaryKeyCheckBox);
                    mainPanel.add(new JLabel("FK:"));
                    mainPanel.add(foreignKeyCheckBox);
                    mainPanel.add(new JLabel("NULL:"));
                    mainPanel.add(allowNullCheckBox);
                    mainPanel.add(new JLabel(""));
                    mainPanel.add(new JLabel(""));
                }

                JButton createButton2 = new JButton("Crear");
                JButton cancelarButton = new JButton("Regresar");

                newRightPanel2.add(mainPanel);
                newRightPanel2.add(createButton2);
                newRightPanel2.add(cancelarButton);
                newRightPanel2.revalidate();
                newRightPanel2.repaint();

            }
        });
        remove(newSplitPane);
        add(newSplitPane2);
        newSplitPane = newSplitPane2;
        revalidate();
    }

        private void createNewSplitPane4(JSplitPane newSplitPane) {

        }

        private JPanel createSplitButton(){
            JPanel newLeftPanel = new JPanel();
            JButton button = new JButton("CREAR BD");

            ImageIcon imageIcon = new ImageIcon("src/main/resources/images/crearBD.png");
            personalizarBoton(button,imageIcon);

            JButton button2 = new JButton("CREAR TABLA");
            ImageIcon imageIcon2 = new ImageIcon("src/main/resources/images/creartabla.png");
            personalizarBoton(button2,imageIcon2);

            JButton button3 = new JButton("LIMPIAR");
            ImageIcon imageIcon3 = new ImageIcon("src/main/resources/images/limpiar.png");
            personalizarBoton(button3,imageIcon3);

            newLeftPanel.add(button);
            newLeftPanel.add(button2);
            newLeftPanel.add(button3);
            newLeftPanel.setLayout(new GridLayout(7, 1));
            newLeftPanel.setPreferredSize(new Dimension(20, 20));
            return newLeftPanel;
        }

        private static void personalizarBoton(JButton button5,ImageIcon imageIcon) {
            Image image = imageIcon.getImage();
            Image newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newimg);
            button5.setIcon(imageIcon);
            button5.setBackground(Color.decode("#F2F2F2"));
        }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InicioBD();
        });
    }

}
