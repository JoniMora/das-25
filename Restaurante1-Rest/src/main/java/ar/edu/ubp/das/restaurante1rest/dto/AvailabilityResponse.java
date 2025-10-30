package ar.edu.ubp.das.restaurante1rest.dto;

import java.time.LocalDate;
import java.util.List;

public record AvailabilityResponse (
        int branchId,
        LocalDate date,
        int adults,
        int kids,
        List<String> slots
)
{}
