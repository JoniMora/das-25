package ar.edu.ubp.das.restaurante1rest.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse
{
        private long codReserva;
        private String branchId;
        private String date;
        private String time;
}

