package co.edu.uniquindio.alquilaFacil.controladores;

import co.edu.uniquindio.alquilaFacil.excepciones.AtributoVacioException;
import co.edu.uniquindio.alquilaFacil.excepciones.InformacionRepetidaException;
import co.edu.uniquindio.alquilaFacil.modelo.AlquilaFacil;
import co.edu.uniquindio.alquilaFacil.modelo.Cliente;
import co.edu.uniquindio.alquilaFacil.modelo.Propiedades;
import co.edu.uniquindio.alquilaFacil.utils.CambioIdiomaEvent;
import co.edu.uniquindio.alquilaFacil.utils.CambioIdiomaListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistroClienteControler implements Initializable, CambioIdiomaListener {

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

    @FXML
    private Label cedula, nombre, correo, ciudad, direccion, telefono;

    //Uso del Singleton
    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();

    public void initialize(URL url, ResourceBundle resourceBundle){
        // Inicialización normal del controlador

        // Registra este controlador como un escuchador de cambios de idioma
        Propiedades.getInstance().addCambioIdiomaListener(this);

        // Actualiza las cadenas de texto según el idioma actual
        actualizarTextos();
    }

    @Override
    public void onCambioIdioma(CambioIdiomaEvent evento) {
        // Se llama cuando se cambia el idioma

        // Actualiza las cadenas de texto según el nuevo idioma
        actualizarTextos();
    }
    private void actualizarTextos(){
        cedula.setText(propiedades.getResourceBundle().getString("TextoCedula"));
        nombre.setText(propiedades.getResourceBundle().getString("TextoNombre"));
        correo.setText(propiedades.getResourceBundle().getString("TextoCorreo"));
        telefono.setText(propiedades.getResourceBundle().getString("TextoTelefono"));
        ciudad.setText(propiedades.getResourceBundle().getString("TextoCiudad"));
        direccion.setText(propiedades.getResourceBundle().getString("TextoDireccion"));
        btnAtras.setText(propiedades.getResourceBundle().getString("TextoAtras"));
        btnGuardar.setText(propiedades.getResourceBundle().getString("TextoGuardar"));
    }

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
