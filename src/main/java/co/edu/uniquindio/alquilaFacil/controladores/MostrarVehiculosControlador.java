package co.edu.uniquindio.alquilaFacil.controladores;

import co.edu.uniquindio.alquilaFacil.modelo.AlquilaFacil;
import co.edu.uniquindio.alquilaFacil.modelo.Cliente;
import co.edu.uniquindio.alquilaFacil.modelo.Propiedades;
import co.edu.uniquindio.alquilaFacil.modelo.Vehiculo;
import co.edu.uniquindio.alquilaFacil.utils.CambioIdiomaEvent;
import co.edu.uniquindio.alquilaFacil.utils.CambioIdiomaListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
;import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MostrarVehiculosControlador implements Initializable, CambioIdiomaListener {

    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();

    private final Propiedades propiedades = Propiedades.getInstance();

    @FXML
    private Button btnAtras;

    @FXML
    private TableView<Vehiculo> tableView = new TableView<>();

    @FXML
    private TableColumn<Vehiculo, String> tableReferencia, tablePlaca, tableMarca;

    @FXML
    private TableColumn<Vehiculo, Integer> tableModelo, tableKm, tableSillas;

    @FXML
    private TableColumn<Vehiculo, Boolean> tableTrasmision;

    @FXML
    private TableColumn<Vehiculo, Float> tablePrecio;

    private ArrayList<Vehiculo> vehiculos = alquilaFacil.getVehiculos();

    private ObservableList<Vehiculo> listaVehiculos = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        // Inicialización normal del controlador
        for(int i = 0; i < vehiculos.size(); i ++){
            listaVehiculos.add(vehiculos.get(i));
        }

        tableReferencia.setCellValueFactory(new PropertyValueFactory<Vehiculo, String>("referencia"));
        tablePlaca.setCellValueFactory(new PropertyValueFactory<Vehiculo, String>("placa"));
        tableMarca.setCellValueFactory(new PropertyValueFactory<Vehiculo, String>("marca"));
        tableModelo.setCellValueFactory(new PropertyValueFactory<Vehiculo, Integer>("modelo"));
        tableKm.setCellValueFactory(new PropertyValueFactory<Vehiculo, Integer>("kilometraje"));
        tablePrecio.setCellValueFactory(new PropertyValueFactory<Vehiculo, Float>("precioDia"));
        tableTrasmision.setCellValueFactory(new PropertyValueFactory<Vehiculo, Boolean>("esAutomatico"));
        tableSillas.setCellValueFactory(new PropertyValueFactory<Vehiculo, Integer>("numSillas"));


        tableView.setItems(listaVehiculos);

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
        tablePlaca.setText(propiedades.getResourceBundle().getString("TextoPLACA"));
        tableReferencia.setText(propiedades.getResourceBundle().getString("TextoREFERENCIA"));
        tableMarca.setText(propiedades.getResourceBundle().getString("TextoMARCA"));
        tableModelo.setText(propiedades.getResourceBundle().getString("TextoMODELO"));
        tableKm.setText(propiedades.getResourceBundle().getString("TextoKM"));
        tablePrecio.setText(propiedades.getResourceBundle().getString("TextoPRECIO"));
        tableTrasmision.setText(propiedades.getResourceBundle().getString("TextoTRANSMISION"));
        tableSillas.setText(propiedades.getResourceBundle().getString("TextoSILLAS"));
    }

    public void atras(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(btnAtras)){
            alquilaFacil.loadStage("/inicio.fxml", event);
        }
    }
}
