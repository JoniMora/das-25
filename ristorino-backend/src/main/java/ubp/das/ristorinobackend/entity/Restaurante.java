package ubp.das.ristorinobackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "restaurantes")
@Data
@NoArgsConstructor
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro_restaurante")
    private Long nroRestaurante;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @Column(name = "cuit", nullable = false, unique = true)
    private String cuit;

    @OneToMany(
            mappedBy = "restaurante", // Mapeado por el campo "restaurante" en SucursalRestaurante
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY // Lazy loading
    )
    private List<SucursalRestaurante> sucursales;
}