package co.edu.uniquindio.alquilaFacil.modelo;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alquiler implements Serializable {

    private Cliente cliente;
    private Vehiculo vehiculo;
    private LocalDateTime fechaRegistro;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private float valorTotal;

}