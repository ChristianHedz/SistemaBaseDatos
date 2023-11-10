package org.sistemajava.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;
import javax.swing.JOptionPane;

public class ConexionBD {

    private String baseDatos;
    private String url;
    private String url2;
    private String usuario;
    private String contrasena;
    private String host;
    private String puerto;

    public ConexionBD(String nombreBase , String usuarioBD, String contrasenaBD, String hostBD, String puertoBD){
        this.baseDatos = nombreBase;
        this.usuario = usuarioBD;
        this.contrasena = contrasenaBD;
        this.host = hostBD;
        this.puerto = puertoBD;
        setBaseDatos();
    }

    public ConexionBD(String usuarioBD, String contrasenaBD, String hostBD, String puertoBD) {
        this.usuario = usuarioBD;
        this.contrasena = contrasenaBD;
        this.host = hostBD;
        this.puerto = puertoBD;
        setBaseDatos();
    }

    public void setBaseDatos(){
        System.out.println("Usuario: " + usuario);
        System.out.println("Contraseña: " + contrasena);
        System.out.println("Host: " + host);;
        System.out.println("Puerto: " + puerto);
        Optional<String> nombreBD = Optional.ofNullable(baseDatos);
        System.out.println("nombreBD: " + baseDatos);

        if (nombreBD.isPresent()){
            url = "jdbc:mysql://"+host+":"+puerto+"/"+baseDatos+"?serverTimezone=America/Mexico_City";
        }else{
            url = "jdbc:mysql://" + host + ":" + puerto;
        }
    }

    public Connection obtenerConexion()  {
        Connection connection = null;
        try {
            System.out.println("url: " + url);
            connection = DriverManager.getConnection(url, usuario, contrasena);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error en la conexión, revisa los valores ingresados", "Mensaje Informativo", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        return connection;
    }


}
