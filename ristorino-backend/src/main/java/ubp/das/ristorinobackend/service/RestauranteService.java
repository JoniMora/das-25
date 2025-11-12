package ubp.das.ristorinobackend.service;

import org.springframework.transaction.annotation.Transactional;
import ubp.das.ristorinobackend.dto.promotion.PromocionDTO;
import ubp.das.ristorinobackend.dto.restaurant.RestauranteResumenDTO;
import ubp.das.ristorinobackend.dto.restaurant.RestauranteDetalleDTO;
import ubp.das.ristorinobackend.dto.restaurant.SucursalDetalleDTO;
import ubp.das.ristorinobackend.entity.Restaurante;
import ubp.das.ristorinobackend.repository.RestauranteRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final PromocionService promocionService;

    private static final Integer CATEGORIA_ID_TIPO_COMIDA = 1;

    public RestauranteService(RestauranteRepository restauranteRepository, PromocionService promocionService) {
        this.restauranteRepository = restauranteRepository;
        this.promocionService = promocionService;
    }

    @Transactional(readOnly = true) // Mantiene la sesion de Hibernate abierta, sino se rompe
    public RestauranteDetalleDTO obtenerDetallePorId(Long restauranteId) {
        Restaurante restaurante = restauranteRepository.findByIdWithDetails(restauranteId)
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado con ID: " + restauranteId));

        RestauranteDetalleDTO dto = new RestauranteDetalleDTO();
        dto.setId(restaurante.getNroRestaurante());
        dto.setNombre(restaurante.getRazonSocial());
        dto.setDescripcion(restaurante.getDescripcion());
        dto.setImagenUrl(restaurante.getImagenUrl());

        List<SucursalDetalleDTO> sucursalesDTO = restaurante.getSucursales().stream()
                .map(SucursalDetalleDTO::fromEntity)
                .collect(Collectors.toList());
        dto.setSucursales(sucursalesDTO);

        List<PromocionDTO> promocionesReales = promocionService.obtenerPromocionesActivasPorRestaurante(restauranteId);
        dto.setPromocionesVigentes(promocionesReales);

        String tipoComida = restaurante.getPreferencias().stream()
                .filter(pref -> pref.getDominio().getCategoria().getCodCategoria().equals(CATEGORIA_ID_TIPO_COMIDA))
                .map(pref -> pref.getDominio().getNomValorDominio())
                .distinct()
                .collect(Collectors.joining(", "));

        if (tipoComida.isEmpty()) {
            dto.setTipoCocina("No especificado");
        } else {
            dto.setTipoCocina(tipoComida);
        }
        return dto;
    }

    // Obtiene una lista resumida de todos los restaurantes.
    @Transactional(readOnly = true)
    public List<RestauranteResumenDTO> obtenerRestaurantesResumen() {
        List<Restaurante> restaurantes = restauranteRepository.findAll(Sort.by(Sort.Direction.ASC, "razonSocial"));

        return restaurantes.stream().map(restaurante -> {

            String tipoComida = restaurante.getPreferencias().stream()
                    .filter(pref -> pref.getDominio().getCategoria().getCodCategoria().equals(CATEGORIA_ID_TIPO_COMIDA))
                    .map(pref -> pref.getDominio().getNomValorDominio())
                    .distinct()
                    .collect(Collectors.joining(", "));

            if (tipoComida.isEmpty()) {
                tipoComida = "No especificado";
            }

            return RestauranteResumenDTO.fromEntity(restaurante, tipoComida);
        }).collect(Collectors.toList());
    }
}