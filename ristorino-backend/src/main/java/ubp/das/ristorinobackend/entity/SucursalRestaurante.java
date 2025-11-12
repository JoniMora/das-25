package ubp.das.ristorinobackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "sucursales_restaurantes")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"restaurante"})
public class SucursalRestaurante {

    @EmbeddedId
    private SucursalRestauranteId id;

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
    private Short totalComensales;

    @Column(name = "nro_localidad")
    private String nroLocalidad;
}