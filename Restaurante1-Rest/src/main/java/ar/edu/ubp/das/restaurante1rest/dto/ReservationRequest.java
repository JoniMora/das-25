package ar.edu.ubp.das.restaurante1rest.dto;

public record ReservationRequest (
        String date,
        String time,
        int adults,
        int kids,
        int clientId,
        Integer zoneId
){}
