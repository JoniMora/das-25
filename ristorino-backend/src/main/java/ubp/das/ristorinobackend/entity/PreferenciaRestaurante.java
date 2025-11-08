package ubp.das.ristorinobackend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "preferencias_restaurantes")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"restaurante", "dominio"})
public class PreferenciaRestaurante {

    @EmbeddedId
    private PreferenciaRestauranteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("nroRestaurante")
    @JoinColumn(name = "nro_restaurante")
    private Restaurante restaurante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "cod_categoria", referencedColumnName = "cod_categoria", insertable = false, updatable = false),
            @JoinColumn(name = "nro_valor_dominio", referencedColumnName = "nro_valor_dominio", insertable = false, updatable = false)
    })
    private DominioCategoriaPreferencia dominio;

    @Column(name = "observaciones")
    private String observaciones;
}