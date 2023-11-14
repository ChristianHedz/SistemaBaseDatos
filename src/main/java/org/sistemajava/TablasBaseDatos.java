package org.sistemajava;

import lombok.Setter;
import org.sistemajava.utils.ConexionBD;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class TablasBaseDatos extends javax.swing.JFrame {
        private JList<String> tableList;
        private DefaultTableModel tableModel;
        private JTable dataTable;
        private JButton createButton;
        private JButton updateButton;
        private JButton deleteButton;
        @Setter
        private String nombreBase;
        private ConexionBD conexion;
        private List<String> camposSeleccionados = new ArrayList<>();

        public TablasBaseDatos(String nombreBaseDatos){
            this.nombreBase = nombreBaseDatos;
            this.conexion  = new ConexionBD(nombreBase,"root","","localhost","3306");
            System.out.println("connection: " + nombreBase);
            System.out.println(conexion.obtenerConexion());
            interfazTablaBaseDatos();
        }

        public void interfazTablaBaseDatos(){
            JFrame frame = new JFrame("Visor de Tablas de BD");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JPanel listPanel = new JPanel();
            listPanel.setLayout(new BorderLayout());

            DefaultListModel<String> listModel = new DefaultListModel<>();
            tableList = new JList<>(listModel);
            listPanel.add(new JScrollPane(tableList), BorderLayout.CENTER);

            JPanel tablePanel = new JPanel();
            tablePanel.setLayout(new BorderLayout());

            tableModel = new DefaultTableModel();
            dataTable = new JTable(tableModel);
            tablePanel.add(new JScrollPane(dataTable), BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            createButton = new JButton("Crear");
            ImageIcon imageIconCrear = new ImageIcon("src/main/resources/images/creartabla.png");
            CreateSplitButtons.personalizarBoton(createButton,imageIconCrear);

            updateButton = new JButton("Actualizar");
            ImageIcon imageIconEditar = new ImageIcon("src/main/resources/images/editarpng.png");
            CreateSplitButtons.personalizarBoton(updateButton,imageIconEditar);

            deleteButton = new JButton("Eliminar");
            ImageIcon imageIconEliminar = new ImageIcon("src/main/resources/images/eliminarpng.png");
            CreateSplitButtons.personalizarBoton(deleteButton,imageIconEliminar);

            buttonPanel.add(createButton);
            buttonPanel.add(updateButton);
            buttonPanel.add(deleteButton);
            //boton para seleccionar tabla con los elemntos de los checkbox seleccionados
            JButton selectButton = new JButton("Seleccionar");
            ImageIcon imageIconSeleccionar = new ImageIcon("src/main/resources/images/seleccionar.png");
            CreateSplitButtons.personalizarBoton(selectButton,imageIconSeleccionar);
            buttonPanel.add(selectButton);

            // Nuevo panel para checkboxes
            JPanel checkboxPanel = new JPanel();
            // Agregar este panel en el lugar donde quieras mostrar los checkboxes.

            frame.add(listPanel, BorderLayout.WEST);
            frame.add(tablePanel, BorderLayout.CENTER);
            frame.add(checkboxPanel, BorderLayout.EAST); // Agregar el panel de checkboxes a la derecha.
            tablePanel.add(buttonPanel, BorderLayout.SOUTH);
            frame.add(listPanel, BorderLayout.WEST);
            frame.add(tablePanel, BorderLayout.CENTER);
            loadTables();

            tableList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        String selectedTable = tableList.getSelectedValue();
                        if (selectedTable != null) {
                            loadTableData(selectedTable);

                        }
                    }
                }
            });

            createButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int selectedRow = dataTable.getSelectedRow();
                        if (selectedRow >= 0) {
                            String selectedTable = tableList.getSelectedValue();
                            if (selectedTable != null) {
                                Vector<Object> rowData = (Vector<Object>) tableModel.getDataVector().elementAt(selectedRow);
                                insertDataIntoTable(selectedTable, rowData);
                                loadTableData(tableList.getSelectedValue());
                                loadTables();
                            }
                        }
                    }catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Ingresa un valor valido", "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int selectedRow = dataTable.getSelectedRow();
                        if (selectedRow >= 0) {
                            String selectedTable = tableList.getSelectedValue();
                            if (selectedTable != null) {
                                int primaryKeyValue = obtenerValorDeClavePrimaria(selectedRow); // Debes implementar esta función para obtener el valor de la clave primaria
                                Vector<Object> rowData = (Vector<Object>) tableModel.getDataVector().elementAt(selectedRow);
                                updateDataInTable(selectedTable, rowData, primaryKeyValue);
                                loadTableData(tableList.getSelectedValue());
                                loadTables();
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Selecciona una fila para actualizar", "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
                    }
                    loadTables();
                }
            });

            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        int selectedRow = dataTable.getSelectedRow();
                        if (selectedRow >= 0) {
                            String selectedTable = tableList.getSelectedValue();
                            if (selectedTable != null) {
                                int primaryKeyValue = obtenerValorDeClavePrimaria(selectedRow); // Reemplaza con la función adecuada para obtener la clave primaria
                                eliminarFilaDeBaseDeDatos(selectedTable, primaryKeyValue); // Implementa esta función
                                eliminarFilaDeTabla(selectedRow); // Implementa esta función para actualizar la tabla en la interfaz de usuario
                            }
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Selecciona una fila a eliminar", "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
                    }
                    loadTables();
                }
            });

            //boton para seleccionar tabla con los elemntos de los checkbox seleccionados
            selectButton.addActionListener(e -> {
                cargarCheckboxes(tableList.getSelectedValue(), checkboxPanel);
                camposSeleccionados.clear();
            });

            frame.setSize(800, 600);
            frame.setVisible(true);
        }

        private void loadTables() {
            DefaultListModel<String> listModel = (DefaultListModel<String>) tableList.getModel();
            listModel.clear();
            try (Connection connection = conexion.obtenerConexion();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SHOW TABLES")) {
                while (resultSet.next()) {
                    listModel.addElement(resultSet.getString(1));
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al cargar las tablas", "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        private void loadTableData(String tableName) {
            tableModel.setColumnCount(0);
            tableModel.setRowCount(0);
            try (Connection connection = conexion.obtenerConexion();
                 Statement statement = connection.createStatement();) {

                String query = "SELECT * FROM " + tableName;
                System.out.println(query);

                ResultSet resultSet = statement.executeQuery(query);
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    tableModel.addColumn(metaData.getColumnName(i));
                }

                tableModel.addRow(new Vector<Object>());

                while (resultSet.next()) {
                    Vector<Object> rowData = new Vector<>();
                    for (int i = 1; i <= columnCount; i++) {
                        rowData.add(resultSet.getObject(i));
                    }
                    tableModel.addRow(rowData);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void insertDataIntoTable(String tableName, Vector<Object> rowData) {
            try  {
                StringBuilder query = new StringBuilder("INSERT INTO ");
                query.append(tableName).append(" (");
                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    query.append(tableModel.getColumnName(i));
                    if (i < tableModel.getColumnCount() - 1) {
                        query.append(", ");
                    }
                }
                query.append(") VALUES (");
                for (int i = 0; i < rowData.size(); i++) {
                    query.append("?");
                    if (i < rowData.size() - 1) {
                        query.append(", ");
                    }
                }
                query.append(")");
                try (Connection connection = conexion.obtenerConexion();
                        PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
                    for (int i = 0; i < rowData.size(); i++) {
                        preparedStatement.setObject(i + 1, rowData.get(i));
                    }
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Datos insertados correctamente.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ingresa los datos correctamente", "Revisa tus datos", JOptionPane.INFORMATION_MESSAGE);

            }
        }

        private int obtenerValorDeClavePrimaria(int selectedRow) {
            int primaryKeyColumnIndex = 0; // Reemplaza con la posición correcta de la columna de la clave primaria
            String primaryKeyValue = dataTable.getValueAt(selectedRow, primaryKeyColumnIndex).toString();
            int primaryKeyIntValue = 0;
            try {
                primaryKeyIntValue = Integer.parseInt(primaryKeyValue);
            } catch (NumberFormatException ex) {
                System.out.println("Error al convertir a int");
            }
            return primaryKeyIntValue;
        }


        private void updateDataInTable(String tableName, Vector<Object> rowData, int primaryKeyValue) {

            try  {
                StringBuilder query = new StringBuilder("UPDATE ");
                query.append(tableName).append(" SET ");
                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    query.append(tableModel.getColumnName(i)).append(" = ?");
                    if (i < tableModel.getColumnCount() - 1) {
                        query.append(", ");
                    }
                }
                query.append(" WHERE id = ?");

                try (Connection connection = conexion.obtenerConexion();
                        PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {
                    for (int i = 0; i < rowData.size(); i++) {
                        preparedStatement.setObject(i + 1, rowData.get(i));
                    }
                    preparedStatement.setInt(rowData.size() + 1, primaryKeyValue); // Reemplaza primaryKeyValue con el valor de tu clave primaria
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Datos actualizados correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontraron registros para actualizar.");
                    }
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al actualizar datos.");
            }
        }

        private void eliminarFilaDeBaseDeDatos(String tableName, int primaryKeyValue) {
            try (Connection connection = conexion.obtenerConexion();
                 Statement statement = connection.createStatement()) {
                String query = "DELETE FROM " + tableName + " WHERE id = " + primaryKeyValue;
                int rowsAffected = statement.executeUpdate(query);
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Fila eliminada correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró la fila para eliminar.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al eliminar la fila.");
            }
        }

        private void eliminarFilaDeTabla(int rowIndex) {
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            model.removeRow(rowIndex);
        }



    private void cargarCheckboxes(String selectedTable, JPanel checkboxPanel) {
        System.out.println("camposSeleccionados antes de cargar los checkboxes: " + camposSeleccionados);
        checkboxPanel.removeAll();
        checkboxPanel.setLayout(new GridLayout(0, 1));

        try (Connection connection = conexion.obtenerConexion();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SHOW COLUMNS FROM " + selectedTable)) {

            while (resultSet.next()) {
                String field = resultSet.getString(1);
                JCheckBox checkBox = new JCheckBox(field);
                checkBox.addActionListener(e -> {
                    if (checkBox.isSelected()) {
                        camposSeleccionados.add(field);
                    } else {
                        camposSeleccionados.remove(field);
                    }
                });
                checkboxPanel.add(checkBox);
            }
            //agregar un checkbox para seleccionar todos los campos
            JCheckBox checkBox = new JCheckBox("Seleccionar todos");
            checkboxPanel.add(checkBox);
            AtomicBoolean selected = new AtomicBoolean(false);
            checkBox.addActionListener(e -> {
                if (checkBox.isSelected()) {
                    camposSeleccionados.clear();
                    selected.set(true);
                } else {
                    camposSeleccionados.clear();
                    selected.set(false);
                }
            });

            System.out.println("camposSeleccionados: " + camposSeleccionados);
            loadTableData2(selectedTable, camposSeleccionados, selected);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar las tablas", "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
        }
        checkboxPanel.revalidate();
        checkboxPanel.repaint();
    }



    private void loadTableData2(String tableName, List<String> camposSeleccionados, AtomicBoolean selected) {
        tableModel.setColumnCount(0);
        tableModel.setRowCount(0);
        try (Connection connection = conexion.obtenerConexion();
             Statement statement = connection.createStatement();) {

            StringBuilder selectPart = new StringBuilder();
            if (!camposSeleccionados.isEmpty()) {
                for (String campo : camposSeleccionados) {
                    selectPart.append('`').append(campo).append("`, ");
                }
                if (selectPart.length() >= 2) {
                    selectPart.delete(selectPart.length() - 2, selectPart.length());
                }
            }

            String selectPartString = selectPart.toString().trim();
            String query = "SELECT * FROM " + tableName;
            System.out.println(query);
            if(selected.get()){
                query = "SELECT * FROM " + tableName;
            } else if (!selectPartString.isEmpty()) {
                query = "SELECT " + selectPartString + " FROM " + tableName;
            }

            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }

            tableModel.addRow(new Vector<Object>());

            while (resultSet.next()) {
                Vector<Object> rowData = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.add(resultSet.getObject(i));
                }
                tableModel.addRow(rowData);
            }
            JOptionPane.showMessageDialog(null,query, "Consulta Exitosa!", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }

