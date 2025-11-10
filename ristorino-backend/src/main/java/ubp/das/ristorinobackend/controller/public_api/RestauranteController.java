package ubp.das.ristorinobackend.controller.public_api;

import ubp.das.ristorinobackend.dto.restaurant.RestauranteDetalleDTO;
import ubp.das.ristorinobackend.dto.restaurant.RestauranteResumenDTO;
import ubp.das.ristorinobackend.service.RestauranteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/restaurants")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    // Devuelve la lista de todos los restaurantes.
    @GetMapping
    public ResponseEntity<List<RestauranteResumenDTO>> getAllRestaurantes() {
        List<RestauranteResumenDTO> restaurantes = restauranteService.obtenerRestaurantesResumen();

        if (restaurantes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<RestauranteDetalleDTO> getDetalleRestaurante(@PathVariable Long restauranteId) {

        RestauranteDetalleDTO detalle = restauranteService.obtenerDetallePorId(restauranteId);

        if (detalle == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(detalle);
    }
}