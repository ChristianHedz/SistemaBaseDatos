package org.sistemajava;

import org.sistemajava.utils.ConexionBD;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class IBasesDatos{

    private static JList<String> databaseList;
    private static DefaultListModel<String> listModel = new DefaultListModel<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Database Image Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Crear la conexión a la base de datos
        ConexionBD conexionBase = new ConexionBD("root", "", "localhost", "3306");
        try (Connection connection = conexionBase.obtenerConexion();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SHOW DATABASES")) {

            // Obtener los nombres de las bases de datos desde la consulta
            List<String> databaseNames = new ArrayList<>();
            while (resultSet.next()) {
                String dbName = resultSet.getString(1);
                databaseNames.add(dbName);
            }

            // Agregar los nombres al modelo de la lista
            for (String dbName : databaseNames) {
                listModel.addElement(dbName);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        databaseList = new JList<>(listModel);
        databaseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel rightPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIconFondo = new ImageIcon("src/main/resources/images/fondoBlackSQL.png");
                Image image = imageIconFondo.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JScrollPane scrollPane = new JScrollPane(rightPanel);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(databaseList),scrollPane);
        frame.getContentPane().add(splitPane);

        databaseList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedDatabase = databaseList.getSelectedValue();
                    if (selectedDatabase != null) {
                        panelCrearTabla(rightPanel,selectedDatabase);
                    }
                }
            }
        });
        frame.setVisible(true);
    }

    private static void panelCrearTabla(JPanel newSplitPane, String databaseName) {
        ConexionBD conexionBD = new ConexionBD(databaseName,"root", "", "localhost", "3306");

        CreateSplitButtons createSplitButtons = new CreateSplitButtons();
        JPanel newLeftPanel2 = createSplitButtons.createSplitButton();
        JButton crearTabla = createSplitButtons.getCrearTablaButton();

        JPanel newRightPanel2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIconFondo = new ImageIcon("src/main/resources/images/fondoBlackSQL.png");
                Image image = imageIconFondo.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        JScrollPane scrollPane = new JScrollPane(newRightPanel2);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        newRightPanel2.setLayout(new GridBagLayout());

        JLabel nameLabel = new JLabel("Nombre Tabla:");
        InicioBD.personalizarLabel(nameLabel);
        JTextField nameTextField = new JTextField(20);

        JLabel charsetLabel = new JLabel("Filas:");
        InicioBD.personalizarLabel(charsetLabel);
        String[] charsets = {"1", "2", "3", "4"};
        JComboBox<String> charsetComboBox = new JComboBox<>(charsets);

        JButton createButton = new JButton("Crear");
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        JLabel labelCrearBase = new JLabel("Crear tabla en la base de datos: " + databaseName);
        InicioBD.personalizarLabel(labelCrearBase);
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

        JSplitPane newSplitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, newLeftPanel2, scrollPane);
        newSplitPane2.setResizeWeight(0.2);

        createButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String tableName = nameTextField.getText();
                int columnCount = Integer.parseInt((String) charsetComboBox.getSelectedItem());

                newRightPanel2.removeAll();

                JPanel mainPanel = new JPanel(new GridLayout(0, 2)); // Una fila, dos columnas
                mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                List<JTextField> columnNameFields = new ArrayList<>();
                List<JComboBox<String>> dataTypeComboBoxes = new ArrayList<>();
                List<JCheckBox> primaryKeyCheckboxes = new ArrayList<>();
                List<JCheckBox> autoIncrementCheckboxes = new ArrayList<>();

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
                            //crear un tabla con las filas y valores de cada una de las filas
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
                newRightPanel2.add(mainPanel);
                newRightPanel2.revalidate();
                newRightPanel2.repaint();
            }
        });
            newSplitPane.remove(newSplitPane);
            newSplitPane.add(newSplitPane2);
            newSplitPane.revalidate();
    }

    public static void createAndShowGUI2() {
        JFrame frame = new JFrame("Database Image Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Crear la conexión a la base de datos
        ConexionBD conexionBase = new ConexionBD("root", "", "localhost", "3306");
        try (Connection connection = conexionBase.obtenerConexion();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SHOW DATABASES")) {

            // Obtener los nombres de las bases de datos desde la consulta
            List<String> databaseNames = new ArrayList<>();
            while (resultSet.next()) {
                String dbName = resultSet.getString(1);
                databaseNames.add(dbName);
            }

            // Agregar los nombres al modelo de la lista
            for (String dbName : databaseNames) {
                listModel.addElement(dbName);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        databaseList = new JList<>(listModel);
        databaseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel rightPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIconFondo = new ImageIcon("src/main/resources/images/fondoBlackSQL.png");
                Image image = imageIconFondo.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JScrollPane scrollPane = new JScrollPane(rightPanel);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(databaseList),scrollPane);
        frame.getContentPane().add(splitPane);

        databaseList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String selectedDatabase = databaseList.getSelectedValue();
                    if (selectedDatabase != null) {
                        panelCrearTabla2(rightPanel,selectedDatabase);
                    }
                }
            }
        });

        frame.setVisible(true);
    }


    private static void panelCrearTabla2(JPanel newSplitPane, String databaseName) {
        ConexionBD conexionBD = new ConexionBD(databaseName,"root", "", "localhost", "3306");

        CreateSplitButtons createSplitButtons = new CreateSplitButtons();
        JPanel newLeftPanel2 = createSplitButtons.createSplitButton();
        JButton crearTabla = createSplitButtons.getCrearTablaButton();

        JPanel newRightPanel2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIconFondo = new ImageIcon("src/main/resources/images/fondoBlackSQL.png");
                Image image = imageIconFondo.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JScrollPane scrollPane = new JScrollPane(newRightPanel2);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        newRightPanel2.setLayout(new GridBagLayout());
        JLabel nameLabel = new JLabel("Nombre Tabla:");
        InicioBD.personalizarLabel(nameLabel);
        JTextField nameTextField = new JTextField(20);

        JLabel charsetLabel = new JLabel("Filas:");
        InicioBD.personalizarLabel(charsetLabel);
        String[] charsets = {"1", "2", "3", "4"};
        JComboBox<String> charsetComboBox = new JComboBox<>(charsets);

        JButton createButton = new JButton("Crear");
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        JLabel labelCrearBase = new JLabel("Crear tabla en la base de datos: " + databaseName);
        InicioBD.personalizarLabel(labelCrearBase);
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

        JSplitPane newSplitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, newLeftPanel2, scrollPane);
        newSplitPane2.setResizeWeight(0.2);

        createButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String tableName = nameTextField.getText();
                int columnCount = Integer.parseInt((String) charsetComboBox.getSelectedItem());

                newRightPanel2.removeAll();

                JPanel mainPanel = new JPanel(new GridLayout(0, 2)); // Una fila, dos columnas
                mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                List<JTextField> columnNameFields = new ArrayList<>();
                List<JComboBox<String>> dataTypeComboBoxes = new ArrayList<>();
                List<JCheckBox> primaryKeyCheckboxes = new ArrayList<>();
                List<JCheckBox> autoIncrementCheckboxes = new ArrayList<>();

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
                            //crear un tabla con las filas y valores de cada una de las filas
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
                newRightPanel2.add(mainPanel);
                newRightPanel2.revalidate();
                newRightPanel2.repaint();
            }
        });
        newSplitPane.remove(newSplitPane);
        newSplitPane.add(newSplitPane2);
        newSplitPane.revalidate();
    }


}

