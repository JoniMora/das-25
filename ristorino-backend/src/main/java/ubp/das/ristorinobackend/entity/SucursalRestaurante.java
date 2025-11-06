package ubp.das.ristorinobackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sucursales_restaurantes")
@Data
@NoArgsConstructor
public class SucursalRestaurante {

    @EmbeddedId
    private SucursalRestauranteId id;

    // Mapeo inverso (ManyToOne) para que la sucursal "conozca" a su restaurante
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("nroRestaurante")
    @JoinColumn(name = "nro_restaurante")
    private Restaurante restaurante;

    @Column(name = "nom_sucursal", nullable = false)
    private String nomSucursal;

    @Column(name = "calle")
    private String calle;

    @Column(name = "nro_calle")
    private String nroCalle;

    @Column(name = "barrio")
    private String barrio;

    @Column(name = "total_comensales")
    private Integer totalComensales;
}