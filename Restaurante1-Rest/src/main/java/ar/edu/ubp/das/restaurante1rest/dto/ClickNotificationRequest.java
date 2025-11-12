package ar.edu.ubp.das.restaurante1rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public  class ClickNotificationRequest {
        private Long restauranteId;
        private int totalClicks;
        private double montoTotal;
}
