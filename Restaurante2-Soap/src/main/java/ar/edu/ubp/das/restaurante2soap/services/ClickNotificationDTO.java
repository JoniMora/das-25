package ar.edu.ubp.das.restaurante2soap.services;

import java.math.BigDecimal;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class ClickNotificationDTO {
    private String restauranteId;
    private int totalClicks;
    private BigDecimal montoTotal;
}

