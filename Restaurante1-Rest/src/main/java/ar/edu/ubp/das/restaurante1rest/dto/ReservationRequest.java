package ar.edu.ubp.das.restaurante1rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReservationRequest {
    private String date;
    private String time;
    private int adults;
    private int kids;
    private String clientId;
    private String zoneId;
}
