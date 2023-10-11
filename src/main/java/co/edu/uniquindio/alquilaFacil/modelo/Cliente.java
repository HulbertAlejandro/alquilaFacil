package co.edu.uniquindio.alquilaFacil.modelo;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente implements Serializable {

    private String cedula;
    private String nombreCompleto;
    private String telefono;
    private String email;
    private String ciudad;
    private String direccion;
}
