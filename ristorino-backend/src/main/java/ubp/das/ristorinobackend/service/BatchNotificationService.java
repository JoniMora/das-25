package ubp.das.ristorinobackend.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ubp.das.ristorinobackend.repository.ContenidoClickRepository;
import ubp.das.ristorinobackend.entity.ContenidoClick;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BatchNotificationService {

    private final ContenidoClickRepository clickRepository;
    private final RestTemplate restTemplate;

    public BatchNotificationService(ContenidoClickRepository clickRepository) {
        this.clickRepository = clickRepository;
        this.restTemplate = new RestTemplate();
    }

    @Transactional
    public void procesarYNotificarClicks() {
        System.out.println("---  Iniciando proceso de notificación  ---");

        List<ContenidoClick> clicksPendientes = clickRepository.findClicksPendientesDeNotificar();

        if (clicksPendientes.isEmpty()) {
            System.out.println("--- No hay clics pendientes. ---");
            return;
        }
        System.out.println("---  Encontrados " + clicksPendientes.size() + " clics para notificar. ---");

        Map<Long, List<ContenidoClick>> clicksPorRestaurante = clicksPendientes.stream()
                .collect(Collectors.groupingBy(clic -> clic.getContenido().getRestaurante().getNroRestaurante()));


        for (Map.Entry<Long, List<ContenidoClick>> entry : clicksPorRestaurante.entrySet()) {
            Long restauranteId = entry.getKey();
            List<ContenidoClick> clicksDelRestaurante = entry.getValue();

            String urlNotificacion = "http://localhost:8081/api/v1/notifications/clicks"; // URL del Restaurante 1

            try {
                Map<String, Object> payload = Map.of(
                        "restauranteId", restauranteId,
                        "totalClicks", clicksDelRestaurante.size(),
                        "montoTotal", clicksDelRestaurante.stream().mapToDouble(c -> c.getCostoClickLiquidar().doubleValue()).sum()
                );

                restTemplate.postForEntity(urlNotificacion, payload, String.class);
                System.out.println("    Notificación enviada a Restaurante ID: " + restauranteId);

                for (ContenidoClick click : clicksDelRestaurante) {
                    click.setNotificado(true);
                }
                clickRepository.saveAll(clicksDelRestaurante);

            } catch (Exception e) {
                System.err.println("  ERROR al notificar a Restaurante ID: " + restauranteId + " - " + e.getMessage());

            }
        }
        System.out.println("--- Proceso finalizado. ---");
    }
}
