package ar.edu.ubp.das.restaurante2soap.services;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class SucursalDTO {
    private int id;
    private String nombre;
    private String barrio;
    private String direccion;
    private Integer capacidad;
    private boolean habilitada;
}

