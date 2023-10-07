package co.edu.uniquindio.alquilaFacil.controladores;

import co.edu.uniquindio.alquilaFacil.modelo.AlquilaFacil;
import co.edu.uniquindio.alquilaFacil.modelo.Propiedades;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class InicioControlador implements Initializable {

    @FXML
    private Button btnRegistrarCliente;

    @FXML
    private Button btnAlquilarVehiculo;

    @FXML
    private Button btnRegistrarVehiculo;

    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();

    private final Propiedades propiedades = Propiedades.getInstance();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        btnRegistrarCliente.setText(propiedades.getResourceBundle().getString("TextoRegistrarCliente"));
        btnAlquilarVehiculo.setText(propiedades.getResourceBundle().getString("TextoAlquilarVehiculo"));
        btnRegistrarVehiculo.setText(propiedades.getResourceBundle().getString("TextoRegistrarVehiculo"));
    }

    public void registrarVehiculo(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(btnRegistrarVehiculo)){
            alquilaFacil.loadStage("/registrarVehiculo.fxml", event);
        }
    }

    public void registrarCliente(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(btnRegistrarCliente)){
            alquilaFacil.loadStage("/registrarCliente.fxml", event);
        }
    }

    public void alquilarVehiculo(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(btnAlquilarVehiculo)){
            alquilaFacil.loadStage("/alquilarVehiculo.fxml", event);
        }
    }
}
