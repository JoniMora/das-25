package ubp.das.ristorinobackend.service;

import ubp.das.ristorinobackend.dto.restaurant.RestauranteDetalleDTO;
import ubp.das.ristorinobackend.dto.restaurant.SucursalDetalleDTO;
import ubp.das.ristorinobackend.entity.Restaurante;
import ubp.das.ristorinobackend.repository.RestauranteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;

    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public RestauranteDetalleDTO obtenerDetallePorId(Long restauranteId) {

        // Busca en RistorinoDB
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado con ID: " + restauranteId));

        // Mapeo de Entidad a DTO
        RestauranteDetalleDTO dto = new RestauranteDetalleDTO();
        dto.setId(restaurante.getNroRestaurante());
        dto.setNombre(restaurante.getRazonSocial());

        List<SucursalDetalleDTO> sucursalesDTO = restaurante.getSucursales()
                .stream()
                .map(SucursalDetalleDTO::fromEntity)
                .collect(Collectors.toList());

        dto.setSucursales(sucursalesDTO);

        // Mapeo de Promociones (simulado, prox Req. 12)
        dto.setPromocionesVigentes(List.of("Simulacion: 2x1 en Aperitivos"));
        dto.setTipoCocina("Italiana"); // Simulacion
        dto.setDescripcion("Descripción simulada de La Bella Pizza."); // Simulacion

        return dto;
    }
}