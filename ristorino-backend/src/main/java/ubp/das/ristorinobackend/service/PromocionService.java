package ubp.das.ristorinobackend.service;

import org.springframework.transaction.annotation.Transactional;
import ubp.das.ristorinobackend.dto.promotion.PromocionDTO;
import ubp.das.ristorinobackend.repository.ContenidoRestauranteRepository;
import ubp.das.ristorinobackend.entity.ContenidoRestaurante;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromocionService {

    private final ContenidoRestauranteRepository repository;

    public PromocionService(ContenidoRestauranteRepository repository) {
        this.repository = repository;
    }

    public List<PromocionDTO> obtenerPromocionesActivas() {
        return repository.findActivePromotions()
                .stream()
                .map(PromocionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<PromocionDTO> obtenerPromocionesActivasPorRestaurante(Long restauranteId) {
        return repository.findActivePromotionsByRestauranteId(restauranteId)
                .stream()
                .map(PromocionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PromocionDTO obtenerPromocionPorCodigo(String codContenido) {

        ContenidoRestaurante contenido = repository.findByCodContenidoRestaurante(codContenido)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada: " + codContenido));

        return PromocionDTO.fromEntity(contenido);
    }
}