package ubp.das.ristorinobackend.controller.Batch;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ubp.das.ristorinobackend.service.BatchNotificationService;

import java.util.Map;

@RestController
@RequestMapping("/v1/admin/batch")
public class BatchController {
    private final BatchNotificationService batchService;

    public BatchController(BatchNotificationService batchService) {
        this.batchService = batchService;
    }

    @PostMapping("/run-click-notifications")
    public ResponseEntity<?> runClickNotificationBatch() {
        try {

            batchService.procesarYNotificarClicks();
            return ResponseEntity.ok(Map.of("message", "Proceso batch de notificación de clics ejecutado exitosamente."));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Fallo al ejecutar el batch: " + e.getMessage()));
        }
    }

}
