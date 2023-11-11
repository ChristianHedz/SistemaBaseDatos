package org.sistemajava;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.swing.*;
import java.awt.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSplitButtons {
    private JButton crearBaseDatosButton;
    private JButton crearTablaButton;
    private JButton limpiarButton;

    public JPanel createSplitButton(){

        this.crearBaseDatosButton = crearBaseDatosButton != null ? crearBaseDatosButton : new JButton("CREAR BD");
        this.crearTablaButton = crearTablaButton != null ? crearTablaButton : new JButton("CREAR TABLA");
        this.limpiarButton = limpiarButton != null ? limpiarButton : new JButton("LIMPIAR");

        JPanel newLeftPanel = new JPanel();
        System.out.println("crearBaseDatosButton: " + crearTablaButton);

        ImageIcon imageIcon = new ImageIcon("src/main/resources/images/crearBD.png");
        personalizarBoton(crearBaseDatosButton,imageIcon);

        ImageIcon imageIcon2 = new ImageIcon("src/main/resources/images/creartabla.png");
        personalizarBoton(crearTablaButton,imageIcon2);

        ImageIcon imageIcon3 = new ImageIcon("src/main/resources/images/limpiar.png");
        personalizarBoton(limpiarButton,imageIcon3);

        JButton regresarButton = new JButton("REGRESAR");
        ImageIcon imageIcon4 = new ImageIcon("src/main/resources/images/atras.png");
        personalizarBoton(regresarButton,imageIcon4);

        JButton emptyButton = new JButton();
        emptyButton.setBackground(Color.decode("#F2F2F2"));
        JButton emptyButton2 = new JButton();
        emptyButton2.setBackground(Color.decode("#F2F2F2"));
        JButton emptyButton3 = new JButton();
        emptyButton3.setBackground(Color.decode("#F2F2F2"));

        newLeftPanel.add(crearBaseDatosButton);
        newLeftPanel.add(emptyButton);
        newLeftPanel.add(crearTablaButton);
        newLeftPanel.add(emptyButton2);
        newLeftPanel.add(limpiarButton);
        newLeftPanel.add(emptyButton3);
        newLeftPanel.add(regresarButton);
        newLeftPanel.setLayout(new GridLayout(7, 1));
        newLeftPanel.setPreferredSize(new Dimension(20, 20));

        crearBaseDatosButton.addActionListener(e ->{
            IBasesDatos.createAndShowGUI();
        });

//        crearTablaButton.addActionListener(e -> {
//            IBASEDATOSCHECK.create
//        });

        regresarButton.addActionListener(e ->{
            InicioBD.cerrarVentanaCrearBase();
            InicioBD inicioBD = new InicioBD();
            inicioBD.ingresarInterfazConexion();
        });

        limpiarButton.addActionListener(e -> {
            InicioBD.limpiarCampos();
        });
        return newLeftPanel;
    }

    public static void personalizarBoton(JButton button5,ImageIcon imageIcon) {
        Image image = imageIcon.getImage();
        Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newimg);
        button5.setIcon(imageIcon);
        button5.setBackground(Color.decode("#F2F2F2"));
    }

}
