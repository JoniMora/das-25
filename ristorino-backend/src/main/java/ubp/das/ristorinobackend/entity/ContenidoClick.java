package ubp.das.ristorinobackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "clicks_contenidos_restaurantes")
@Data
@NoArgsConstructor
public class ContenidoClick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro_click")
    private Long nroClick;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "nro_restaurante", referencedColumnName = "nro_restaurante"),
            @JoinColumn(name = "nro_idioma", referencedColumnName = "nro_idioma"),
            @JoinColumn(name = "nro_contenido", referencedColumnName = "nro_contenido")
    })
    private ContenidoRestaurante contenido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nro_cliente") // Nullable si el clic es anónimo
    private Cliente cliente;

    @Column(name = "fecha_hora_registro", nullable = false)
    private LocalDateTime fechaHoraClick;

    @Column(name = "costo_click_liquidar", nullable = false)
    private BigDecimal costoClickLiquidar;

    @Column(name = "notificado", nullable = false)
    private Boolean notificado = false; // Control para el batch
}
