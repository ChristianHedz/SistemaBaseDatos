package org.sistemajava;

import javax.swing.*;

public class ConectarBD {
    private JSplitPane splitPane;
    private JPanel leftPanel;

    public ConectarBD(JSplitPane splitPane, JPanel leftPanel){
        this.splitPane = splitPane;
        this.leftPanel = leftPanel;
    }

//    private void createNewSplitPanes() {
//        JPanel newLeftPanel = new JPanel();
//        // Agrega el nuevo contenido al newLeftPanel según tus necesidades
//        // Ejemplo: newLeftPanel.add(new JLabel("Nuevo Contenido Izquierdo"));
//
//        JPanel newRightPanel = new JPanel();
//        // Agrega el nuevo contenido al newRightPanel según tus necesidades
//        // Ejemplo: newRightPanel.add(new JLabel("Nuevo Contenido Derecho"));
//
//        JSplitPane newSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, newLeftPanel, newRightPanel);
//        newSplitPane.setResizeWeight(0.2);
//
//        // Reemplaza el JSplitPane anterior con los nuevos
//        remove(splitPane);
//        add(newSplitPane);
//        splitPane = newSplitPane;
//
//        revalidate(); // Revalida la interfaz gráfica para reflejar los cambios
//    }
}
