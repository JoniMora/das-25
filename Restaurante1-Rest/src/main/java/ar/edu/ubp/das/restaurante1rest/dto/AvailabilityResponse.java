package ar.edu.ubp.das.restaurante1rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityResponse
{
    private String branchId;
    private LocalDate date;
    private int adults;
    private int kids;
    private List<String> slots;
}
