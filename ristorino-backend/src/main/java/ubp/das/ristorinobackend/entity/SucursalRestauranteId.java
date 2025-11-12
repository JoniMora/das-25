package ubp.das.ristorinobackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class SucursalRestauranteId implements Serializable {

    @Column(name = "nro_restaurante")
    private Long nroRestaurante;

    @Column(name = "nro_sucursal")
    private Short nroSucursal;
}