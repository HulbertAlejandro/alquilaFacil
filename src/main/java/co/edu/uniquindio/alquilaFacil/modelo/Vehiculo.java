package co.edu.uniquindio.alquilaFacil.modelo;



import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehiculo implements Serializable {

    private static final long serialVersionUID = 1L; // Puedes elegir cualquier número, asegúrate de actualizarlo si realizas cambios en la clase

    private String placa;
    private String referencia;
    private String marca;
    private int modelo;
    private String foto;
    private int kilometraje;
    private float precioDia;
    private boolean esAutomatico;
    private int numSillas;

}
