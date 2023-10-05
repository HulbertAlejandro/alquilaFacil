package co.edu.uniquindio.alquilaFacil.controladores;

import co.edu.uniquindio.alquilaFacil.excepciones.AtributoVacioException;
import co.edu.uniquindio.alquilaFacil.excepciones.InformacionRepetidaException;
import co.edu.uniquindio.alquilaFacil.modelo.AlquilaFacil;
import co.edu.uniquindio.alquilaFacil.modelo.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistroClienteControler {

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtCiudad;

    @FXML
    private TextField txtTelefono;

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnGuardar;

    //Uso del Singleton
    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();

    public void registrarCliente(ActionEvent actionEvent){

        try {
            Cliente cliente = alquilaFacil.registrarCliente(
                    txtCedula.getText(),
                    txtNombre.getText(),
                    txtCorreo.getText(),
                    txtDireccion.getText(),
                    txtCiudad.getText(),
                    txtTelefono.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Se ha registrado correctamente el cliente "+cliente.getNombreCompleto());
            alert.show();

        } catch (AtributoVacioException | InformacionRepetidaException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setHeaderText(null);
            alert.show();

        }

    }

    public void atras(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(btnAtras)){
            alquilaFacil.loadStage("/inicio.fxml", event);
        }
    }
}
