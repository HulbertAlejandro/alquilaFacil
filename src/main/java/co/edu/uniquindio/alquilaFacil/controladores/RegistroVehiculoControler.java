package co.edu.uniquindio.alquilaFacil.controladores;

import co.edu.uniquindio.alquilaFacil.excepciones.AtributoNegativoException;
import co.edu.uniquindio.alquilaFacil.excepciones.AtributoVacioException;
import co.edu.uniquindio.alquilaFacil.excepciones.InformacionRepetidaException;
import co.edu.uniquindio.alquilaFacil.modelo.AlquilaFacil;

import co.edu.uniquindio.alquilaFacil.modelo.Propiedades;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RegistroVehiculoControler implements Initializable{

    @FXML
    private TextField txtPlaca;

    @FXML
    private TextField txtReferencia;

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtUrl;

    @FXML
    private TextField txtKm;

    @FXML
    private TextField txtNumPuertas;

    @FXML
    private TextField txtPrecioDia;

    @FXML
    private ComboBox<String> opcionesCaja;

    @FXML
    private Button btnAtras;

    @FXML
    private Button btnGuardar;

    @FXML
    private Label labelReferencia, placa, marca, modelo, km, precio, labelTransmision, numeroSillas, labelUrl;

    //Uso del Singleton
    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        opcionesCaja.setItems( FXCollections.observableArrayList( List.of("Automática", "Manual") ) );
        labelReferencia.setText(propiedades.getResourceBundle().getString("TextoReferencia"));
        placa.setText(propiedades.getResourceBundle().getString("TextoPlaca"));
        marca.setText(propiedades.getResourceBundle().getString("TextoMarca"));
        modelo.setText(propiedades.getResourceBundle().getString("TextoModelo"));
        precio.setText(propiedades.getResourceBundle().getString("TextoPrecio"));
        km.setText(propiedades.getResourceBundle().getString("TextoKm"));
        numeroSillas.setText(propiedades.getResourceBundle().getString("TextoNumeroSillas"));
        labelTransmision.setText(propiedades.getResourceBundle().getString("TextoTransmision"));
        labelUrl.setText(propiedades.getResourceBundle().getString("TextoUrl"));
        btnAtras.setText(propiedades.getResourceBundle().getString("TextoAtras"));
        btnGuardar.setText(propiedades.getResourceBundle().getString("TextoGuardar"));
    }

    public void registrarVehiculo(ActionEvent actionEvent){

        try {
            alquilaFacil.registrarVehiculo(
                    txtPlaca.getText(),
                    txtReferencia.getText(),
                    txtMarca.getText(),
                    Integer.parseInt(txtModelo.getText()),
                    txtUrl.getText(),
                    Integer.parseInt(txtKm.getText()),
                    Float.parseFloat(txtPrecioDia.getText()),
                    opcionesCaja.getValue().equals("Automática"),
                    Integer.parseInt(txtNumPuertas.getText())

            );

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("El vehículo se ha registrado correctamente");
            alert.setHeaderText(null);
            alert.show();

        } catch (AtributoNegativoException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.setHeaderText(null);
            alert.show();

        } catch (NumberFormatException e1){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Tenga en cuenta que el número de puertas, modelo, precio por día y kilometraje deben ser números enteros");
            alert.setHeaderText(null);
            alert.show();
        } catch (AtributoVacioException | InformacionRepetidaException e) {
            throw new RuntimeException(e);
        }
    }

    public void atras(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(btnAtras)){
            alquilaFacil.loadStage("/inicio.fxml", event);
        }
    }
}