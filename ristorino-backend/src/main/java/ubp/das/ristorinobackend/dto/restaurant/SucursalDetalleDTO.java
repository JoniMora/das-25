package ubp.das.ristorinobackend.dto.restaurant;

import lombok.Data;
import lombok.Builder;
import ubp.das.ristorinobackend.entity.SucursalRestaurante;

@Data
@Builder
public class SucursalDetalleDTO {

    private Integer nroSucursal;
    private String nombre;
    private String direccion;
    private String barrio;
    private Integer capacidad;

    public static SucursalDetalleDTO fromEntity(SucursalRestaurante entity) {
        return SucursalDetalleDTO.builder()
                .nroSucursal(entity.getId().getNroSucursal())
                .nombre(entity.getNomSucursal())
                .direccion(entity.getCalle() + " " + entity.getNroCalle())
                .barrio(entity.getBarrio())
                .capacidad(entity.getTotalComensales())
                .build();
    }
}