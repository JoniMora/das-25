package ubp.das.ristorinobackend.dto.restaurant;

import lombok.Data;
import java.util.List;

@Data
public class RestauranteDetalleDTO {

    private Long id;
    private String nombre;
    private String tipoCocina;
    private String descripcion;
    private String imagenUrl;

    private List<SucursalDetalleDTO> sucursales;
    private List<String> promocionesVigentes;
}