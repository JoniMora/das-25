package ar.edu.ubp.das.restaurante1rest.controller;

import ar.edu.ubp.das.restaurante1rest.dto.ClickNotificationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private final JdbcTemplate jdbc;
    public NotificationController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    @PostMapping("/clicks")
    public ResponseEntity<Map<String, String>> receiveClickNotification(
            @RequestBody ClickNotificationRequest request
    ) {
        System.out.println("--------------------------------------------------");
        System.out.println("  [            -La Bella Pizza -                 ");
        System.out.println("  ¡Notificación de liquidación RECIBIDA del Portal!");
        System.out.println("  Restaurante ID: " + request.getRestauranteId());
        System.out.println("  Total de Clics: " + request.getTotalClicks());
        System.out.println("  Monto a liquidar: $" + request.getMontoTotal());
        System.out.println("--------------------------------------------------");
        try {
            String sql = "INSERT INTO dbo.clicks_contenidos (nro_restaurante, nro_contenido, nro_cliente, costo_click) VALUES (?, ?, NULL, ?)";
            String miIdRestauranteLocal = "REST-1";
            String miContenidoPlaceholder = "PROMO-001";
            jdbc.update(sql,
                    miIdRestauranteLocal,
                    miContenidoPlaceholder,
                    request.getMontoTotal()
            );
            System.out.println("  ¡Notificación GUARDADA en la base de datos local (Restaurante1)!");

        } catch (Exception e) {
            System.err.println("  Error al guardar la notificación en la BD local: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
        return ResponseEntity.ok(Map.of(
                "status", "received",
                "message", "Notificacion de clics recibida por La Bella Pizza."
        ));
    }
}
