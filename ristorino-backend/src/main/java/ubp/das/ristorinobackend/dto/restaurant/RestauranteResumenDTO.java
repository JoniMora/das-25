package ubp.das.ristorinobackend.dto.restaurant;

import lombok.Builder;
import lombok.Data;
import ubp.das.ristorinobackend.entity.Restaurante;

@Data
@Builder
public class RestauranteResumenDTO {

    private Long id;
    private String nombre;
    private String tipoCocina;
    private String imagenUrl;

    public static RestauranteResumenDTO fromEntity(Restaurante entity, String tipoCocina) {
        return RestauranteResumenDTO.builder()
                .id(entity.getNroRestaurante())
                .nombre(entity.getRazonSocial())
                .tipoCocina(tipoCocina)
                .imagenUrl(entity.getImagenUrl())
                .build();
    }
}