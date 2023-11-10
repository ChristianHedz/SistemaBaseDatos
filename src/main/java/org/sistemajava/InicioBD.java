package org.sistemajava;

import org.sistemajava.utils.ConexionBD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InicioBD extends JFrame {
    private JSplitPane splitPane;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JButton button1;
    private JButton button2;
    private JLabel imageLabel;
    private static JFrame newFrame;
    private InterfazConexion testConexion;
    private ConexionBD conexionBD = new ConexionBD("root", "", "localhost", "3306");

    public void interfazInicial(){
        setTitle("Bienvenido a mi entorno grafico de base de datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 720);
        setBackground(Color.BLACK);
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(7, 1));
        button1 = new JButton("Conectar");
        ImageIcon imageIconi = new ImageIcon("src/main/resources/images/logo.png");
        personalizarBoton(button1,imageIconi);
        button1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        leftPanel.add(button1);
        //hacer mas largo el button1
        button1.setPreferredSize(new Dimension(70, 30));
        rightPanel = new JPanel();
        rightPanel.setBackground(Color.BLACK);
        rightPanel.setLayout(new BorderLayout());
        imageLabel = new JLabel();
        rightPanel.add(new JLabel("Bienvenido a mi aplicación de Java Swing"));
        ImageIcon imageIcon = new ImageIcon("src/main/resources/images/logo.png");
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(600, 600,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        imageLabel.setIcon(imageIcon);
        rightPanel.add(imageLabel, BorderLayout.CENTER);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.2);
        add(splitPane);
        setLocationRelativeTo(null);
        setVisible(true);

        button1.addActionListener(e -> {
            ingresarInterfazConexion();
            dispose();
        });


    }

    public void ingresarInterfazConexion(){
                splitPaneTestConnection();
                dispose();
    }

    public void splitPaneTestConnection() {
        JPanel newLeftPanel = new JPanel();

        JButton testButton = new JButton("TEST BD");
        ImageIcon imageIcon = new ImageIcon("src/main/resources/images/testBD.png");
        personalizarBoton(testButton,imageIcon);

        JButton emptyButton = new JButton("");
        JButton emptyButton2 = new JButton("");
        JButton emptyButton3 = new JButton("");
        emptyButton.setBackground(Color.decode("#F2F2F2"));
        emptyButton2.setBackground(Color.decode("#F2F2F2"));
        emptyButton3.setBackground(Color.decode("#F2F2F2"));

        JButton limpiarButton = new JButton("LIMPIAR");
        ImageIcon imageIcon2 = new ImageIcon("src/main/resources/images/base.png");
        personalizarBoton(limpiarButton,imageIcon2);

        JButton regresarButton = new JButton("ATRAS");
        ImageIcon imageIcon3 = new ImageIcon("src/main/resources/images/atras.png");
        personalizarBoton(regresarButton,imageIcon3);

        newLeftPanel.add(testButton);
        newLeftPanel.add(emptyButton);
        newLeftPanel.add(limpiarButton);
        newLeftPanel.add(emptyButton2);
        newLeftPanel.add(regresarButton);
        newLeftPanel.add(emptyButton3);
        newLeftPanel.setLayout(new GridLayout(7, 1));
        newLeftPanel.setPreferredSize(new Dimension(20, 20));

        testConexion = new InterfazConexion(splitPane, newLeftPanel);
        testConexion.interfazConexion();

        limpiarButton.addActionListener(e -> {
            testConexion.limpiarCampos();
        });

        regresarButton.addActionListener(e -> {
            testConexion.cerrarVentana();
            interfazInicial();
        });
    }

    public void createNewSplitPane2(JSplitPane newSplitPane) {

            CreateSplitButtons createSplitButtons = new CreateSplitButtons();
            JButton crearBase = new JButton("Crear Tabla en BD");
            createSplitButtons.setCrearBaseDatosButton(crearBase);
            JButton crearTabla = new JButton("VER BD'S");
            createSplitButtons.setCrearTablaButton(crearTabla);
            JPanel newLeftPanel2 = createSplitButtons.createSplitButton();


        JPanel newRightPanel2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIconFondo = new ImageIcon("src/main/resources/images/fondoBlackSQL.png");
                Image image = imageIconFondo.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
            newRightPanel2.setLayout(new GridBagLayout());
            JLabel nameLabel = new JLabel("Nombre Base de Datos:");
            personalizarLabel(nameLabel);
            JTextField nameTextField = new JTextField(20);

            JLabel charsetLabel = new JLabel("Charset:");
            personalizarLabel(charsetLabel);
            String[] charsets = {"utf8", "ISO-8859-1", "UTF-16"};
            //colocar charsets validos
            JComboBox<String> charsetComboBox = new JComboBox<>(charsets);

            JButton createButton = new JButton("Crear");
            ImageIcon imageIcon = new ImageIcon("src/main/resources/images/crearBD.png");
            personalizarBoton(createButton,imageIcon);
            createButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;


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
            JScrollPane scrollPane = new JScrollPane(newRightPanel2);
            scrollPane.getViewport().setOpaque(false);
            scrollPane.setOpaque(false);
            JSplitPane newSplitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, newLeftPanel2, scrollPane);
            newSplitPane2.setResizeWeight(0.2);

        createButton.addActionListener(e -> {
            String charset = (String) charsetComboBox.getSelectedItem();
            try (Connection connection = conexionBD.obtenerConexion();
                 Statement statement = connection.createStatement()) {
                String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS `" + nameTextField.getText() + "` CHARACTER SET '" + charset + "'";

                statement.executeUpdate(createDatabaseQuery);
                JOptionPane.showMessageDialog(null, "Base de datos creada con éxito", "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
                createNewSplitPane3(newSplitPane2, nameTextField.getText());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al crear la base de datos", "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
            }
        });


            remove(newSplitPane);
            add(newSplitPane2);
            newSplitPane = newSplitPane2;


            newFrame = new JFrame("INTERFAZ-CREAR BASE DE DATOS");
            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newFrame.getContentPane().add(newSplitPane);
            newFrame.setSize(1200, 720);
            newFrame.setLocationRelativeTo(null);
            newFrame.setVisible(true);
            revalidate();

        }

        public static void cerrarVentanaCrearBase(){
            newFrame.dispose();
        }

    private void createNewSplitPane3(JSplitPane newSplitPane, String databaseName) throws SQLException {
        ConexionBD conexionBD = new ConexionBD(databaseName,"root", "", "localhost", "3306");

        CreateSplitButtons createSplitButtons = new CreateSplitButtons();
        JButton crearBase = new JButton("VER BD'S");
        createSplitButtons.setCrearBaseDatosButton(crearBase);
        JButton crearTabla = new JButton("CREAR TABLA");
        createSplitButtons.setCrearTablaButton(crearTabla);
        JPanel newLeftPanel2 = createSplitButtons.createSplitButton();

        JPanel newRightPanel2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIconFondo = new ImageIcon("src/main/resources/images/fondoBlackSQL.png");
                Image image = imageIconFondo.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        newRightPanel2.setLayout(new GridBagLayout());
        JLabel nameLabel = new JLabel("Nombre Tabla:");
        personalizarLabel(nameLabel);
        JTextField nameTextField = new JTextField(20);

        JLabel charsetLabel = new JLabel("Filas:");
        personalizarLabel(charsetLabel);
        String[] charsets = {"1", "2", "3", "4"};
        JComboBox<String> charsetComboBox = new JComboBox<>(charsets);

        JButton createButton = new JButton("Crear");
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel labelCrearBase = new JLabel("Crear tabla en la base de datos: ");
        newRightPanel2.add(labelCrearBase, gbc);
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

        JScrollPane scrollPane = new JScrollPane(newRightPanel2);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        JSplitPane newSplitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, newLeftPanel2, scrollPane);
        newSplitPane2.setResizeWeight(0.2);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if (charsetComboBox.getSelectedItem().equals("0")){
//                    ConexionBD conexionBase = new ConexionBD(databaseName,"root", "", "localhost", "3306");
//                    try(Connection connection = conexionBase.obtenerConexion()){
//                        PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `" + nameTextField.getText() + "`");
//                        statement.executeUpdate();
//                        JOptionPane.showMessageDialog(null, "Tabla creada con éxito", "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
//                        TablasBaseDatos tablasBaseDatos = new TablasBaseDatos(databaseName);
//                    }catch (Exception ex){
//                        ex.printStackTrace();
//                        JOptionPane.showMessageDialog(null, "Error al crear la tabla", "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
//                    }
//                }
                String tableName = nameTextField.getText();
                newRightPanel2.removeAll();
                JPanel mainPanel = new JPanel(new GridLayout(0, 2)); // Una fila, dos columnas
                mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                List<JTextField> columnNameFields = new ArrayList<>();
                List<JComboBox<String>> dataTypeComboBoxes = new ArrayList<>();
                List<JCheckBox> primaryKeyCheckboxes = new ArrayList<>();
                List<JCheckBox> autoIncrementCheckboxes = new ArrayList<>();

                int columnCount = Integer.parseInt((String) charsetComboBox.getSelectedItem());
                for (int i = 0; i < columnCount; i++) {
                    JTextField columnNameField = new JTextField(20);
                    JComboBox<String> dataTypeComboBox = new JComboBox<>(new String[]{"Texto", "Número", "Fecha"});
                    JCheckBox primaryKeyCheckBox = new JCheckBox("PK");
                    JCheckBox autoIncrementCheckBox = new JCheckBox("AutoIncrement");

                    columnNameFields.add(columnNameField);
                    dataTypeComboBoxes.add(dataTypeComboBox);
                    primaryKeyCheckboxes.add(primaryKeyCheckBox);
                    autoIncrementCheckboxes.add(autoIncrementCheckBox);

                    mainPanel.add(new JLabel("Columna " + (i + 1) + ": Nombre:"));
                    mainPanel.add(columnNameField);
                    mainPanel.add(new JLabel("Tipo de Dato:"));
                    mainPanel.add(dataTypeComboBox);
                    mainPanel.add(new JLabel("PK:"));
                    mainPanel.add(primaryKeyCheckBox);
                    mainPanel.add(new JLabel("AutoIncrement:"));
                    mainPanel.add(autoIncrementCheckBox);

                    crearTabla.addActionListener(event ->{
                        ConexionBD conexionBase = new ConexionBD(databaseName,"root", "", "localhost", "3306");
                        try(Connection connection = conexionBase.obtenerConexion();
                            Statement statement = connection.createStatement()) {
                            String createTableQuery = "CREATE TABLE `" + tableName + "` (";
                            for (int j = 0; j < columnCount; j++) {
                                String columnName = columnNameFields.get(j).getText();
                                String dataType = dataTypeComboBoxes.get(j).getSelectedItem().toString();
                                boolean isPrimaryKey = primaryKeyCheckboxes.get(j).isSelected();
                                boolean isAutoIncrement = autoIncrementCheckboxes.get(j).isSelected();

                                createTableQuery += "`" + columnName + "` ";
                                switch (dataType) {
                                    case "Texto":
                                        createTableQuery += "VARCHAR(" + 100 + ")";
                                        break;
                                    case "Número":
                                        createTableQuery += "INT";
                                        break;
                                    case "Fecha":
                                        createTableQuery += "DATE";
                                        break;
                                }

                                if (isAutoIncrement) {
                                    createTableQuery += " AUTO_INCREMENT";
                                }

                                if (isPrimaryKey) {
                                    createTableQuery += " PRIMARY KEY";
                                }

                                if (j < columnCount - 1) {
                                    createTableQuery += ", ";
                                }
                            }

                            createTableQuery += ")";
                            statement.executeUpdate(createTableQuery);

                            JOptionPane.showMessageDialog(null, "Tabla creada con éxito", "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
                            TablasBaseDatos tablasBaseDatos = new TablasBaseDatos(databaseName);
                        }catch (Exception ex){
                        }
                    });
                }
                //leer los datos de la tabla
                newRightPanel2.add(mainPanel);
                newRightPanel2.revalidate();
                newRightPanel2.repaint();

            }
        });

        remove(newSplitPane);
        add(newSplitPane2);
        newSplitPane = newSplitPane2;

        JFrame newFrame = new JFrame("Nueva Interfaz");
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newFrame.getContentPane().add(newSplitPane);
        newFrame.setSize(1200,720);
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);
        revalidate();
    }


        private static void personalizarBoton(JButton button5,ImageIcon imageIcon) {
            Image image = imageIcon.getImage();
            Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newimg);
            button5.setIcon(imageIcon);
            button5.setBackground(Color.decode("#F2F2F2"));
        }

        public static void personalizarLabel(JLabel label){
            label.setFont(new Font("Roboto", Font.BOLD, 20));
            label.setForeground(Color.WHITE);
        }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InicioBD().interfazInicial();
        });
    }

}
