package ubp.das.ristorinobackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "restaurantes")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"sucursales", "preferencias", "contenidos"})
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro_restaurante")
    private Long nroRestaurante;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @Column(name = "cuit", nullable = false, unique = true)
    private String cuit;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @OneToMany(
            mappedBy = "restaurante",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Set<SucursalRestaurante> sucursales = new HashSet<>();

    @OneToMany(
            mappedBy = "restaurante",
            fetch = FetchType.LAZY
    )
    private Set<PreferenciaRestaurante> preferencias = new HashSet<>();

    @OneToMany(
            mappedBy = "restaurante",
            fetch = FetchType.LAZY
    )
    private Set<ContenidoRestaurante> contenidos = new HashSet<>();
}