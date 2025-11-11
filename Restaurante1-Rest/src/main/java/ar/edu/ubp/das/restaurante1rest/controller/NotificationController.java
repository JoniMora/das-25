package ar.edu.ubp.das.restaurante1rest.controller;


import ar.edu.ubp.das.restaurante1rest.dto.ClickNotificationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificationController {
    @PostMapping("/clicks")
    public ResponseEntity<Map<String, String>> receiveClickNotification(
            @RequestBody ClickNotificationRequest request
    ) {

        System.out.println("--------------------------------------------------");
        System.out.println("🍕 [            -La Bella Pizza -                 ");
        System.out.println("  ¡Notificación de liquidación RECIBIDA del Portal!");
        System.out.println("  Restaurante ID: " + request.getRestauranteId());
        System.out.println("  Total de Clics: " + request.getTotalClicks());
        System.out.println("  Monto a liquidar: $" + request.getMontoTotal());
        System.out.println("--------------------------------------------------");

        return ResponseEntity.ok(Map.of(
                "status", "received",
                "message", "Notificacion de clics recibida por La Bella Pizza."
        ));
    }
}
