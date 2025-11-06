package ubp.das.ristorinobackend.dto.promotion;

import lombok.Builder;
import lombok.Data;
import ubp.das.ristorinobackend.entity.ContenidoRestaurante;

@Data
@Builder
public class PromocionDTO {

    private String codContenido;
    private String titulo;
    private String descripcion;
    private String imagenUrl;
    private Long restauranteId;
    private String nombreRestaurante;

    public static PromocionDTO fromEntity(ContenidoRestaurante entity) {
        return PromocionDTO.builder()
                .codContenido(entity.getCodContenidoRestaurante())
                .titulo(entity.getTitulo())
                .descripcion(entity.getDescripcion())
                .imagenUrl(entity.getImagenUrl())
                .restauranteId(entity.getRestaurante().getNroRestaurante())
                .nombreRestaurante(entity.getRestaurante().getRazonSocial())
                .build();
    }
}