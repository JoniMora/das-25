package ubp.das.ristorinobackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import java.io.Serializable;

@Embeddable
@Data
public class PreferenciaRestauranteId implements Serializable {

    @Column(name = "nro_restaurante")
    private Long nroRestaurante;

    @Column(name = "cod_categoria")
    private Integer codCategoria;

    @Column(name = "nro_valor_dominio")
    private Integer nroValorDominio;

    @Column(name = "nro_preferencia")
    private Integer nroPreferencia;
}