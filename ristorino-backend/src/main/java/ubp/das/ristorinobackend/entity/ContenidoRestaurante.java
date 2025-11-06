package ubp.das.ristorinobackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contenidos_restaurantes")
@Data
@NoArgsConstructor
public class ContenidoRestaurante {

    @EmbeddedId
    private ContenidoRestauranteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("nroRestaurante")
    @JoinColumn(name = "nro_restaurante")
    private Restaurante restaurante;

    @Column(name = "contenido_a_publicar")
    private String titulo;

    @Column(name = "contenido_promocional")
    private String descripcion;

    @Column(name = "imagen_promocional")
    private String imagenUrl;

    @Column(name = "fecha_ini_vigencia")
    private LocalDate fechaIniVigencia;

    @Column(name = "fecha_fin_vigencia")
    private LocalDate fechaFinVigencia;

    @Column(name = "costo_click")
    private BigDecimal costoClick;

    @Column(name = "cod_contenido_restaurante", unique = true, nullable = false)
    private String codContenidoRestaurante;
}