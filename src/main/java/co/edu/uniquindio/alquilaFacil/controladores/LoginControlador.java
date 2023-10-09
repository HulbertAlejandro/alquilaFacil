package co.edu.uniquindio.alquilaFacil.controladores;

import co.edu.uniquindio.alquilaFacil.modelo.AlquilaFacil;
import co.edu.uniquindio.alquilaFacil.modelo.Propiedades;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginControlador implements Initializable {

    @FXML
    private TextField textUser;

    @FXML
    private TextField textContrasena;

    @FXML
    private Button btnEntrar;

    @FXML
    private Button btnCambiarIdioma;

    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();

    private boolean esIngles = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización normal del controlador
    }

    @FXML
    private void cambiarIdioma(ActionEvent event) {
        // Cambia el idioma
        if (esIngles) {
            Propiedades.getInstance().setLocale(new Locale("es", "ES"));
        } else {
            Propiedades.getInstance().setLocale(Locale.ENGLISH);
        }

        // Invierte el valor de esIngles para la próxima vez
        esIngles = !esIngles;

        // Actualiza la interfaz de usuario
        actualizarTextos();
    }

    private void actualizarTextos(){
        btnCambiarIdioma.setText(propiedades.getResourceBundle().getString("TextoCambiarIdioma"));
        textUser.setPromptText(propiedades.getResourceBundle().getString("TextoUsuario"));
        textContrasena.setPromptText(propiedades.getResourceBundle().getString("TextoContrasena"));
        btnEntrar.setText(propiedades.getResourceBundle().getString("TextoEntrar"));
    }

    public void entrar(ActionEvent event) {

        Object evt = event.getSource();

        if(evt.equals(btnEntrar)){
            if(textUser.getText().equals("Alejandro Arango") && textContrasena.getText().equals("1104804234")){
                alquilaFacil.loadStage("/inicio.fxml", event);
                alquilaFacil.crearVehiculo();
            } else{
                if(textUser.getText().isEmpty() && textContrasena.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Error, completar todos los campos","ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                } else{
                    JOptionPane.showMessageDialog(null, "Error, el usuario o contraseña son incorrectos","ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}
