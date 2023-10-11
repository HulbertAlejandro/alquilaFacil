package co.edu.uniquindio.alquilaFacil.controladores;

import co.edu.uniquindio.alquilaFacil.modelo.AlquilaFacil;
import co.edu.uniquindio.alquilaFacil.modelo.Cliente;
import co.edu.uniquindio.alquilaFacil.modelo.Propiedades;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MostrarClientesControlador implements Initializable, CambioIdiomaListener {

    private final AlquilaFacil alquilaFacil = AlquilaFacil.getInstance();
    private final Propiedades propiedades = Propiedades.getInstance();

    @FXML
    private Button btnAtras;

    @FXML
    private TableView<Cliente> tableView = new TableView<>();

    @FXML
    private TableColumn<Cliente, String> tableNombre, tableCedula, tableTelefono, tableCorreo, tableCiudad, tableDireccion;

    private ArrayList<Cliente> clientes = alquilaFacil.getClientes();

    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        for(int i = 0; i < clientes.size(); i ++){
            listaClientes.add(clientes.get(i));
        }

        tableCedula.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cedula"));
        tableNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombreCompleto"));
        tableTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
        tableCorreo.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));
        tableCiudad.setCellValueFactory(new PropertyValueFactory<Cliente, String>("ciudad"));
        tableDireccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));

        tableView.setItems(listaClientes);

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
        tableNombre.setText(propiedades.getResourceBundle().getString("TextoNOMBRE"));
        tableTelefono.setText(propiedades.getResourceBundle().getString("TextoTELEFONO"));
        tableCorreo.setText(propiedades.getResourceBundle().getString("TextoCORREO"));
        tableCiudad.setText(propiedades.getResourceBundle().getString("TextoCIUDAD"));
        tableDireccion.setText(propiedades.getResourceBundle().getString("TextoDIRECCION"));

    }
    public void atras(ActionEvent event){

        Object evt = event.getSource();

        if(evt.equals(btnAtras)){
            alquilaFacil.loadStage("/inicio.fxml", event);
        }
    }
}
