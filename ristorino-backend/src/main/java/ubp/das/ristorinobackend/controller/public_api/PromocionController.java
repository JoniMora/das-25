package ubp.das.ristorinobackend.controller.public_api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ubp.das.ristorinobackend.dto.promotion.ClickRequest;
import ubp.das.ristorinobackend.dto.promotion.PromocionDTO;
import ubp.das.ristorinobackend.service.PromocionService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

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
    // Req. 50: Angular Notifica del click
    @PostMapping("/click")
    public ResponseEntity<?> registerClick(@Valid @RequestBody ClickRequest request) {
        try {
            promocionService.registrarClick(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Click registrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }
}