package ar.edu.ubp.das.restaurante1rest.controller;


import ar.edu.ubp.das.restaurante1rest.dto.AvailabilityResponse;
import ar.edu.ubp.das.restaurante1rest.service.AvailabilityService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/branches/{branchId}/availability")
public class AvailabilityController {
    private final AvailabilityService service;
    public AvailabilityController(AvailabilityService service) { this.service = service; }

    @GetMapping
    public AvailabilityResponse get(
            @PathVariable int branchId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam int adults,
            @RequestParam(defaultValue = "0") int kids
    ) {
        return service.getAvailability(branchId, date, adults, kids);
    }
}
