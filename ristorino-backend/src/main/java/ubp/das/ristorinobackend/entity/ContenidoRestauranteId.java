package ubp.das.ristorinobackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class ContenidoRestauranteId implements Serializable {

    @Column(name = "nro_restaurante")
    private Long nroRestaurante;

    @Column(name = "nro_idioma")
    private Integer nroIdioma;

    @Column(name = "nro_contenido")
    private Integer nroContenido;
}