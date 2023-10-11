package co.edu.uniquindio.alquilaFacil.controladores;

import co.edu.uniquindio.alquilaFacil.modelo.*;
import co.edu.uniquindio.alquilaFacil.utils.CambioIdiomaEvent;
import co.edu.uniquindio.alquilaFacil.utils.CambioIdiomaListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MostrarAlquileresControlador implements Initializable, CambioIdiomaListener {

    @FXML
    private Button btnAtras;

    @FXML
    private TableView<Alquiler> tableView;

    @FXML
    private TableColumn<Alquiler, String> tableCedula;

    @FXML
    private TableColumn<Alquiler, String> tablePlaca;

    @FXML
    private TableColumn<Alquiler, String> tableRegistro;

    @FXML
    private TableColumn<Alquiler, String> tableInicio;

    @FXML
    private TableColumn<Alquiler, String> tableFin;

    @FXML
    private TableColumn<Alquiler, Float> tableValor;

    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();

    private ArrayList<Alquiler> alquileres = alquilaFacil.getAlquileres();

    private ObservableList<Alquiler> listaAlquileres = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        // Inicialización normal del controlador

        for(int i = 0; i < alquileres.size(); i ++){
            listaAlquileres.add(alquileres.get(i));
        }

        tableCedula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getCedula()));
        tablePlaca.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getVehiculo().getPlaca()));
        tableRegistro.setCellValueFactory(new PropertyValueFactory<Alquiler, String>("fechaRegistro"));
        tableInicio.setCellValueFactory(new PropertyValueFactory<Alquiler, String>("fechaInicio"));
        tableFin.setCellValueFactory(new PropertyValueFactory<Alquiler, String>("fechaFin"));
        tableValor.setCellValueFactory(new PropertyValueFactory<Alquiler, Float>("valorTotal"));

        tableView.setItems(listaAlquileres);

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

    private void actualizarTextos() {
        btnAtras.setText(propiedades.getResourceBundle().getString("TextoAtras"));
        tableCedula.setText(propiedades.getResourceBundle().getString("TextoCEDULA"));
        tablePlaca.setText(propiedades.getResourceBundle().getString("TextoPLACA"));
        tableRegistro.setText(propiedades.getResourceBundle().getString("TextoFechaRegistro"));
        tableInicio.setText(propiedades.getResourceBundle().getString("TextoFechaInicio"));
        tableFin.setText(propiedades.getResourceBundle().getString("TextoFechaFin"));
        tableValor.setText(propiedades.getResourceBundle().getString("TextoValor"));
    }

    public void atras(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(btnAtras)){
            alquilaFacil.loadStage("/inicio.fxml", event);
        }
    }
}
