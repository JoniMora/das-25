package ubp.das.ristorinobackend.service;

import ubp.das.ristorinobackend.dto.promotion.PromocionDTO;
import ubp.das.ristorinobackend.repository.ContenidoRestauranteRepository;
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
}