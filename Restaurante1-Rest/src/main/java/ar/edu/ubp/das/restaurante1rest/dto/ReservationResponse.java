package ar.edu.ubp.das.restaurante1rest.dto;

public record ReservationResponse (
        long codReserva,
        int branchId,
        String date,
        String time
){}
