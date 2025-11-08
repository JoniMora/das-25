package ubp.das.ristorinobackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dominio_categorias_preferencias")
@Data
@NoArgsConstructor
public class DominioCategoriaPreferencia {

    @EmbeddedId
    private DominioCategoriaPreferenciaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("codCategoria") // Mapea del EmbeddedId
    @JoinColumn(name = "cod_categoria")
    private CategoriaPreferencia categoria;

    @Column(name = "nom_valor_dominio", nullable = false)
    private String nomValorDominio;
}