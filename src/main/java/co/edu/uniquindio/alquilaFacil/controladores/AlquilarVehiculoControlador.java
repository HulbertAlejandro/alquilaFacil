package co.edu.uniquindio.alquilaFacil.controladores;

import co.edu.uniquindio.alquilaFacil.excepciones.AtributoVacioException;
import co.edu.uniquindio.alquilaFacil.modelo.AlquilaFacil;
import co.edu.uniquindio.alquilaFacil.modelo.Alquiler;
import co.edu.uniquindio.alquilaFacil.modelo.Propiedades;
import co.edu.uniquindio.alquilaFacil.modelo.Vehiculo;
import co.edu.uniquindio.alquilaFacil.utils.CambioIdiomaEvent;
import co.edu.uniquindio.alquilaFacil.utils.CambioIdiomaListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AlquilarVehiculoControlador implements Initializable, CambioIdiomaListener {

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtPlaca;

    @FXML
    private TextField txtReferencia;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtKm;

    @FXML
    private TextField txtSillas;

    @FXML
    private ImageView imagen;

    @FXML
    private TextField txtAutomatico;

    @FXML
    private DatePicker fechaAlquiler;

    @FXML
    private DatePicker fechaRegreso;

    @FXML
    private DatePicker fechaRegistro;

    @FXML
    private ComboBox<String> cbxVehiculos;

    @FXML
    private Button btnAtras, btnBuscar, btnGuardar;

    @FXML
    private Label fechaAlquiler1, fechaRegreso1, cedula, referencia, placa, marca, modelo, precio, km, numeroSillas, automatico, imagen1;


    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();

    private ArrayList<Vehiculo> vehiculos = alquilaFacil.getVehiculos();

    ObservableList<String> referencias = FXCollections.observableArrayList();
    private final Propiedades propiedades = Propiedades.getInstance();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

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
        fechaAlquiler1.setText(propiedades.getResourceBundle().getString("TextoFechaAlquiler"));
        fechaRegreso1.setText(propiedades.getResourceBundle().getString("TextoFechaRegreso"));
        cedula.setText(propiedades.getResourceBundle().getString("TextoCedula"));
        referencia.setText(propiedades.getResourceBundle().getString("TextoReferencia"));
        placa.setText(propiedades.getResourceBundle().getString("TextoPlaca"));
        marca.setText(propiedades.getResourceBundle().getString("TextoMarca"));
        modelo.setText(propiedades.getResourceBundle().getString("TextoModelo"));
        precio.setText(propiedades.getResourceBundle().getString("TextoPrecio"));
        km.setText(propiedades.getResourceBundle().getString("TextoKm"));
        numeroSillas.setText(propiedades.getResourceBundle().getString("TextoNumeroSillas"));
        automatico.setText(propiedades.getResourceBundle().getString("TextoAutomatico"));
        imagen1.setText(propiedades.getResourceBundle().getString("TextoImagen"));
        btnAtras.setText(propiedades.getResourceBundle().getString("TextoAtras"));
        btnGuardar.setText(propiedades.getResourceBundle().getString("TextoGuardar"));
        btnBuscar.setText(propiedades.getResourceBundle().getString("TextoBuscar"));
    }


    public void atras(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(btnAtras)){
            alquilaFacil.loadStage("/inicio.fxml", event);
        }
    }

    public void buscar(ActionEvent event) {

        Object evt = event.getSource();

        if(evt.equals(btnBuscar)){
            String referencia = cbxVehiculos.getSelectionModel().getSelectedItem();
            Vehiculo vehiculo = alquilaFacil.obtenerReferencia(referencia);
            txtPlaca.setText(vehiculo.getPlaca());
            txtModelo.setText(""+ vehiculo.getModelo());
            txtKm.setText("" + vehiculo.getKilometraje());
            Image image = new Image(vehiculo.getFoto());
            imagen.setImage(image);
            txtReferencia.setText(vehiculo.getReferencia());
            txtMarca.setText(vehiculo.getMarca());
            txtPrecio.setText("" + vehiculo.getPrecioDia());
            txtSillas.setText("" + vehiculo.getNumSillas());

        }
    }

    public void registrarAlquiler(ActionEvent event){
        try {
            Alquiler alquiler = alquilaFacil.registrarAlquiler(
                    txtCedula.getText(),
                    txtPlaca.getText(),
                    fechaAlquiler.getValue(),
                    fechaRegreso.getValue());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Se ha alquilado el vehiculo exitosamente");
            alert.show();

        } catch (AtributoVacioException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setHeaderText(null);
            alert.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
