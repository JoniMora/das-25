package ubp.das.ristorinobackend.controller.public_api;

import org.springframework.web.bind.annotation.PathVariable;
import ubp.das.ristorinobackend.dto.promotion.PromocionDTO;
import ubp.das.ristorinobackend.service.PromocionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/v1/promotions")
public class PromocionController {

    private final PromocionService promocionService;

    public PromocionController(PromocionService promocionService) {
        this.promocionService = promocionService;
    }

    // Req. 12: Endpoint para la página de inicio (Home)
    @GetMapping
    public ResponseEntity<List<PromocionDTO>> getActivePromotions() {
        List<PromocionDTO> promos = promocionService.obtenerPromocionesActivas();

        if (promos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(promos);
    }

    // Devuelve una promocion especifica por su codigo.
    @GetMapping("/{codContenido}")
    public ResponseEntity<PromocionDTO> getPromotionByCode(@PathVariable String codContenido) {
        try {
            PromocionDTO promo = promocionService.obtenerPromocionPorCodigo(codContenido);
            return ResponseEntity.ok(promo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}