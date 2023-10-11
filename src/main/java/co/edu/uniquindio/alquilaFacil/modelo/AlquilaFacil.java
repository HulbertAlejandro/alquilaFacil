package co.edu.uniquindio.alquilaFacil.modelo;

import co.edu.uniquindio.alquilaFacil.excepciones.AtributoNegativoException;
import co.edu.uniquindio.alquilaFacil.excepciones.AtributoVacioException;
import co.edu.uniquindio.alquilaFacil.excepciones.InformacionRepetidaException;
import co.edu.uniquindio.alquilaFacil.utils.ArchivoUtils;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


@Getter
public class AlquilaFacil implements Serializable {

    private static final String RUTA_CLIENTES = "src/main/resources/persistencia/clientes.txt";

    private static final String RUTA_VEHICULOS = "src/main/resources/persistencia/vehiculos.txt";

    private static final String RUTA_ALQUILERES = "src/main/resources/persistencia/alquileres.ser";

    private ArrayList<Vehiculo> vehiculos;

    private ArrayList<Cliente> clientes;

    private ArrayList<Alquiler> alquileres;

    private static final Logger LOGGER = Logger.getLogger(AlquilaFacil.class.getName());

    //Variable que tendrá la instancia de esta clase
    private static AlquilaFacil alquilaFacil;

    /**
     * Constructor debe ser privado para que ninguna otra clase pueda crear instancias de esta clase
     */
    private AlquilaFacil(){

        LOGGER.log( Level.INFO, "Se crea una nueva instancia de AlquilaFacil" );
        this.vehiculos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.alquileres = new ArrayList<>();

        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter( new SimpleFormatter());
            LOGGER.addHandler(fh);
            leerCliente();
            leerVehiculo();
            leerAlquiler();

        }catch (IOException e){
            LOGGER.log( Level.SEVERE, e.getMessage() );
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Método que se usará en las otras clases que requieran una instancia de esta clase
     * @return Instancia del objeto AlquilaFacil
     */
    public static AlquilaFacil getInstance(){
        if(alquilaFacil == null){
            alquilaFacil = new AlquilaFacil();
        }

        return alquilaFacil;
    }

    public Cliente registrarCliente(String cedula, String nombreCompleto, String telefono, String email,String ciudad, String direccion) throws AtributoVacioException, InformacionRepetidaException, IOException {

        if(cedula == null || cedula.isBlank()){
            LOGGER.log( Level.WARNING, "La cédula es obligatoria para el registro" );
            throw new AtributoVacioException("La cédula es obligatoria");
        }
        if(nombreCompleto == null || nombreCompleto.isBlank()){
            LOGGER.log( Level.WARNING, "El nombre es obligatorio para el registro" );
            throw new AtributoVacioException("El nombre es obligatorio");
        }
        if(telefono == null || telefono.isBlank()){
            LOGGER.log( Level.WARNING, "El telefono es obligatorio para el registro" );
            throw new AtributoVacioException("El telefono es obligatorio");
        }
        if(email == null || email.isBlank()){
            LOGGER.log( Level.WARNING, "El email es obligatorio para el registro" );
            throw new AtributoVacioException("El email es obligatorio");
        }
        if(ciudad == null || ciudad.isBlank()){
            LOGGER.log( Level.WARNING, "La ciudad es obligatoria para el registro" );
            throw new AtributoVacioException("La ciudad es obligatoria");
        }
        if(direccion == null || direccion.isBlank()){
            LOGGER.log( Level.WARNING, "La direccion es obligatoria para el registro" );
            throw new AtributoVacioException("La direccion es obligatoria");
        }

        if( obtenerCliente(cedula) != null ){
            LOGGER.log( Level.SEVERE, "La cédula "+cedula+" ya esta registrada" );
            throw new InformacionRepetidaException("La cédula "+cedula+" ya está registrada");
        }

        //Demás validaciones

        Cliente cliente = Cliente.builder()
                .cedula(cedula)
                .nombreCompleto(nombreCompleto)
                .telefono(telefono)
                .email(email)
                .ciudad(ciudad)
                .direccion(direccion)
                .build();

        clientes.add(cliente);

        LOGGER.log(Level.INFO, "Se ha registrado un nuevo cliente con la cédula: " + cedula);

        String clienteTxt = cliente.getCedula() + ";" + cliente.getNombreCompleto() + ";" +
                cliente.getTelefono() + ";" + cliente.getEmail() + ";" +
                cliente.getCiudad() + ";" + cliente.getDireccion();

        ArrayList<String> lineas = new ArrayList<>();
        lineas.add(clienteTxt);

        ArchivoUtils.escribirArchivoBufferedWriter(RUTA_CLIENTES, lineas, true);

        return cliente;
    }

    public Vehiculo registrarVehiculo(String placa,String referencia,String marca,int modelo,String foto,int kilometraje,float precioDia,boolean esAutomatico,int numSillas) throws AtributoNegativoException, InformacionRepetidaException, AtributoVacioException, IOException{

        //Validar atributos

        if(kilometraje < 0){
            throw new AtributoNegativoException("El kilometraje no puede ser negativo");
        }
        if(placa == null || placa.isBlank()){
            LOGGER.log( Level.WARNING, "La placa es obligatoria para el registro" );
            throw new AtributoVacioException("La placa es obligatoria");
        }
        if(referencia == null || referencia.isBlank()){
            LOGGER.log( Level.WARNING, "La referencia es obligatoria para el registro" );
            throw new AtributoVacioException("La referencia es obligatoria");
        }
        if(marca == null || marca.isBlank()){
            LOGGER.log( Level.WARNING, "La marca es obligatoria para el registro" );
            throw new AtributoVacioException("La marca es obligatoria");
        }
        if(foto == null || foto.isBlank()){
            LOGGER.log( Level.WARNING, "La foto es obligatoria para el registro" );
            throw new AtributoVacioException("La foto es obligatoria");
        }
        if( obtenerVehiculo(placa) != null ){
            LOGGER.log( Level.SEVERE, "La placa "+placa+" ya esta registrada" );
            throw new InformacionRepetidaException("La placa "+ placa +" ya está registrada");
        }

        Vehiculo vehiculo = Vehiculo.builder()
                .placa(placa)
                .referencia(referencia)
                .marca(marca)
                .modelo(modelo)
                .foto(foto)
                .kilometraje(kilometraje)
                .precioDia(precioDia)
                .esAutomatico(esAutomatico)
                .numSillas(numSillas)
                .build();

        vehiculos.add(vehiculo);

        LOGGER.log(Level.INFO, "Se ha registrado un vehiculo nuevo con la placa: " + placa);

        String vehiculoTxt = vehiculo.getPlaca() + ";" + vehiculo.getReferencia() + ";" + vehiculo.getMarca() + ";" + vehiculo.getModelo() + ";" + vehiculo.getFoto() + ";" + vehiculo.getKilometraje() + ";" + vehiculo.getPrecioDia() + ";" + Boolean.toString(vehiculo.isEsAutomatico()) + ";" + vehiculo.getNumSillas();

        ArrayList<String> lineas = new ArrayList<>();
        lineas.add(vehiculoTxt);

        ArchivoUtils.escribirArchivoBufferedWriter(RUTA_VEHICULOS, lineas, true);

        return vehiculo;

    }

    public Alquiler registrarAlquiler(String cedulaCliente,String placaVehiculo,LocalDate fechaInicio,LocalDate fechaFin) throws Exception, AtributoVacioException, InformacionRepetidaException, IOException{

        //Validar atributos
        if(cedulaCliente == null || cedulaCliente.isBlank()){
            LOGGER.log( Level.WARNING, "La cédula es obligatoria para el registro" );
            throw new AtributoVacioException("La cédula es obligatoria");
        }

        if( obtenerCliente(cedulaCliente) == null ){
            LOGGER.log( Level.SEVERE, "La cédula "+cedulaCliente+" no esta registrada" );
            throw new InformacionRepetidaException("La cédula "+cedulaCliente+" no está registrada");
        }

        if(placaVehiculo == null || placaVehiculo.isBlank()){
            LOGGER.log( Level.WARNING, "La placa es obligatoria para el registro" );
            throw new AtributoVacioException("La placa es obligatoria");
        }

        if(fechaInicio == null){
            LOGGER.log(Level.WARNING, "La fecha de incio es obligatoria para realizar el alquiler");
            throw new AtributoVacioException("La fecha de inicio es obligatoria");
        }

        if(fechaFin == null){
            LOGGER.log(Level.WARNING, "La fecha final es obligatoria para realizar el alquiler");
            throw new AtributoVacioException("La fecha final es obligatoria");
        }

        if(fechaInicio.isAfter(fechaFin)){
            throw new Exception("La fecha de inicio no puede ser después de la fecha final");
        }

        //Diferencia de días
        long dias = fechaInicio.until(fechaFin, ChronoUnit.DAYS);

        //Buscar el cliente y el vehículo y agregarlo al objeto alquiler.

        Alquiler alquiler = Alquiler.builder()
                .cliente(obtenerCliente(cedulaCliente))
                .vehiculo(obtenerVehiculo(placaVehiculo))
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .fechaRegistro(LocalDateTime.now())
                .valorTotal(dias * obtenerVehiculo(placaVehiculo).getPrecioDia())
                .build();

        alquileres.add(alquiler);

        LOGGER.log(Level.INFO, "El alquier del vehiculo: " + placaVehiculo + " ha sido exitoso");

        ArchivoUtils.serializarObjeto(RUTA_ALQUILERES, alquiler, true);

        return alquiler;
    }

    public Cliente obtenerCliente(String cedula){

        for(Cliente c : clientes){
            if(c.getCedula().equals(cedula)){
                return c;
            }
        }

        return null;
    }

    public Vehiculo obtenerVehiculo(String placa){

        for(Vehiculo v : vehiculos){
            if(v.getPlaca().equals(placa)){
                return v;
            }
        }

        return null;
    }

    public void loadStage(String url, Event event){

        try {
            ((Node)(event.getSource())).getScene().getWindow().hide();

            Parent root = FXMLLoader.load(Objects.requireNonNull(AlquilaFacil.class.getResource(url)));
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Alquila Facil");
            newStage.show();

        } catch (Exception ignored) {

        }
    }

    public Vehiculo obtenerReferencia(String referencia){
        for(Vehiculo v: vehiculos){
            if(v.getReferencia().equals(referencia)){
                return v;
            }
        }

        return null;
    }

    public void leerCliente()throws IOException {
        ArrayList<String> clientes = ArchivoUtils.leerArchivoScanner(RUTA_CLIENTES);

        for (String s : clientes) {
            String linea = s;
            String[] valores = linea.split(";");
            this.clientes.add(Cliente.builder()
                    .cedula(valores[0])
                    .nombreCompleto(valores[1])
                    .telefono(valores[5])
                    .email(valores[2])
                    .ciudad(valores[4])
                    .direccion(valores[3])
                    .build());
        }
    }

    public void leerVehiculo() throws IOException{
        ArrayList<String> vehiculos = ArchivoUtils.leerArchivoScanner(RUTA_VEHICULOS);

        for(String s : vehiculos){
            String linea = s;
            String[] valores = linea.split(";");
            this.vehiculos.add(Vehiculo.builder()
                    .placa(valores[0])
                    .referencia(valores[1])
                    .marca(valores[2])
                    .modelo(Integer.parseInt(valores[3]))
                    .foto(valores[4])
                    .kilometraje(Integer.parseInt(valores[5]))
                    .precioDia(Float.parseFloat(valores[6]))
                    .esAutomatico(Boolean.parseBoolean(valores[7]))
                    .numSillas(Integer.parseInt(valores[8]))
                    .build());
        }
    }

    public void leerAlquiler() throws IOException, ClassNotFoundException {
        Object objeto = ArchivoUtils.deserializarObjeto(RUTA_ALQUILERES);
        if (objeto instanceof Alquiler) {
            Alquiler alquiler = (Alquiler) objeto; // Convertirlo a Alquiler
            this.alquileres.add(alquiler);
        }
    }

}