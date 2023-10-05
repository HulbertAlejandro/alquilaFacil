package co.edu.uniquindio.alquilaFacil.controladores;

import co.edu.uniquindio.alquilaFacil.modelo.AlquilaFacil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class InicioControlador {

    @FXML
    private Button btnRegistrarCliente;

    @FXML
    private Button btnAlquilarVehiculo;

    @FXML
    private Button btnRegistrarVehiculo;

    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();

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
