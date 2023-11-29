package org.sistemajava;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class DatabaseModelViewer extends JFrame {
    private String databaseName;

    public DatabaseModelViewer(String databaseName) {
        this.databaseName = databaseName;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setTitle("Database Model Viewer");
    }

    public void showDatabaseModel() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, "root", "");

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(databaseName, null, "%", null);

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();

        try {
            int x = 20;
            int y = 20;
            int tablesInRow = 0;
            int tablesPerRow = 3; // Ajusta la cantidad de tablas por fila
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                Object tableNode = graph.insertVertex(parent, null, tableName, x, y, 120, 60);
                ResultSet foreignKeys = metaData.getImportedKeys(null, null, tableName);

                ResultSet columns = metaData.getColumns(databaseName, null, tableName, "%");

                int columnCount = 0;
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    Object columnNode = graph.insertVertex(parent, null, columnName, x, y + 80 + columnCount * 40, 80, 30);
                    graph.insertEdge(parent, null, "", columnNode, tableNode, "endArrow=classic;strokeWidth=1;");
                    columnCount++;
                }

                while (foreignKeys.next()) {
                    String sourceTable = foreignKeys.getString("FKTABLE_NAME");
                    String sourceColumn = foreignKeys.getString("FKCOLUMN_NAME");
                    String targetTable = foreignKeys.getString("PKTABLE_NAME");
                    String targetColumn = foreignKeys.getString("PKCOLUMN_NAME");
                    System.out.println("Total keys: " + foreignKeys.getFetchSize());
                    System.out.println("FK: " + sourceTable + "." + sourceColumn);
                    System.out.println("PK: " + targetTable + "." + targetColumn);

                    Object sourceNode = findNode(graph, sourceTable);
                    Object targetNode = findNode(graph, targetTable);

                    if (sourceNode != null && targetNode != null) {
                        // Buscar nodos específicos para las claves primarias y foráneas
                        Object sourceKeyNode = findNode(graph, sourceColumn);
                        Object targetKeyNode = findNode(graph, targetColumn);

                        if (sourceKeyNode != null && targetKeyNode != null) {
                            graph.insertEdge(parent, null, "", sourceKeyNode, targetKeyNode, "endArrow=classic;strokeWidth=1;");
                        }
                    }
                }

                columns.close();

                tablesInRow++;

                if (tablesInRow < tablesPerRow) {
                    x += 180; // Ajusta la separación entre tablas
                } else {
                    tablesInRow = 0;
                    x = 20;
                    y += 200; // Ajusta la separación vertical entre filas
                }
            }

            // Crear aristas para las relaciones (claves foráneas)

        } finally {
            graph.getModel().endUpdate();
        }

        mxCompactTreeLayout layout = new mxCompactTreeLayout(graph, false);
        layout.setHorizontal(false); // Establece la disposición en vertical
        layout.setEdgeRouting(true);
        layout.setLevelDistance(50); // Ajusta la distancia entre niveles
        layout.setNodeDistance(10); // Ajusta la distancia entre nodos
        layout.execute(graph.getDefaultParent());

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
    }

    private Object findNode(mxGraph graph, String nodeName) {
        Object[] cells = graph.getChildCells(graph.getDefaultParent());
        for (Object cell : cells) {
            if (graph.getLabel(cell).equals(nodeName)) {
                return cell;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                DatabaseModelViewer viewer = new DatabaseModelViewer("java_hibernate_curso");
                viewer.showDatabaseModel();
                viewer.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
