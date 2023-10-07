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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AlquilaFacil {

    @Getter
    private ArrayList<Vehiculo> vehiculos;

    @Getter
    private ArrayList<Cliente> clientes;

    @Getter
    private ArrayList<Alquiler> alquileres;

    private static final Logger LOGGER = Logger.getLogger(AlquilaFacil.class.getName());

    //Variable que tendrá la instancia de esta clase
    private static AlquilaFacil alquilaFacil;

    /**
     * Constructor debe ser privado para que ninguna otra clase pueda crear instancias de esta clase
     */
    private AlquilaFacil(){

        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter( new SimpleFormatter());
            LOGGER.addHandler(fh);
        }catch (IOException e){
            LOGGER.log( Level.SEVERE, e.getMessage() );
        }

        LOGGER.log( Level.INFO, "Se crea una nueva instancia de AlquilaFacil" );
        this.vehiculos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.alquileres = new ArrayList<>();
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

    public Cliente registrarCliente(String cedula, String nombreCompleto, String telefono, String email,String ciudad, String direccion) throws AtributoVacioException, InformacionRepetidaException{

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
                .email(email)
                .direccion(direccion)
                .ciudad(ciudad)
                .telefono(telefono)
                .build();

        clientes.add(cliente);

        LOGGER.log(Level.INFO, "Se ha registrado un nuevo cliente con la cédula: " + cedula);

        return cliente;
    }

    public Vehiculo registrarVehiculo(String placa,String referencia,String marca,int modelo,String foto,int kilometraje,float precioDia,boolean esAutomatico,int numSillas) throws AtributoNegativoException, InformacionRepetidaException, AtributoVacioException{

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

        return vehiculo;

    }

    public Alquiler registrarAlquiler(String cedulaCliente,String placaVehiculo,LocalDate fechaInicio,LocalDate fechaFin) throws Exception, AtributoVacioException{

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
                .build();

        alquileres.add(alquiler);

        LOGGER.log(Level.INFO, "El alquier del vehiculo: " + placaVehiculo + " ha sido exitoso");

        JOptionPane.showMessageDialog(null, "Factura" + "\nEl costo de alquier del vehiculo es de: " + (dias * obtenerVehiculo(placaVehiculo).getPrecioDia()) + " por: " + dias + " dias");

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

    public Vehiculo crearVehiculo(){
        Vehiculo vehiculo = Vehiculo.builder()
                .placa("UPY-20D")
                .referencia("Spark GT")
                .marca("Chevrolet")
                .modelo(2024)
                .foto("C:/Users/hulbe/IdeaProjects/alquilaFacil/src/main/resources/imagenes/SparkGT.png")
                .kilometraje(0)
                .precioDia(50000)
                .esAutomatico(false)
                .numSillas(5)
                .build();

        vehiculos.add(vehiculo);

        Vehiculo vehiculo1 = Vehiculo.builder()
                .placa("JAL-19M")
                .referencia("Picanto")
                .marca("Kia")
                .modelo(2023)
                .foto("C:/Users/hulbe/IdeaProjects/alquilaFacil/src/main/resources/imagenes/Picanto.png")
                .kilometraje(0)
                .precioDia(60000)
                .esAutomatico(true)
                .numSillas(5)
                .build();

        vehiculos.add(vehiculo1);

        return null;
    }

}