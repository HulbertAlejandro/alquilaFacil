package co.edu.uniquindio.alquilaFacil.modelo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    private String cedula;
    private String nombreCompleto;
    private String telefono;
    private String email;
    private String ciudad;
    private String direccion;
}
