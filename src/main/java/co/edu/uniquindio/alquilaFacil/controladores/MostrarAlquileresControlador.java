package co.edu.uniquindio.alquilaFacil.controladores;

import co.edu.uniquindio.alquilaFacil.modelo.AlquilaFacil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;




public class MostrarAlquileresControlador {

    @FXML
    private Button btnAtras;

    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();

    public void atras(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(btnAtras)){
            alquilaFacil.loadStage("/inicio.fxml", event);
        }
    }
}
