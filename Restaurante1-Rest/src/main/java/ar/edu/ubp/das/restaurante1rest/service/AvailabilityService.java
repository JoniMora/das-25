package ar.edu.ubp.das.restaurante1rest.service;

import ar.edu.ubp.das.restaurante1rest.dto.AvailabilityResponse;
import ar.edu.ubp.das.restaurante1rest.repo.Store;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvailabilityService {
    private final Store store;
    public AvailabilityService(Store store) { this.store = store; }

    public AvailabilityResponse getAvailability(int branchId, LocalDate date, int adults, int kids) {
        Assert.isTrue(adults >= 1, "Debe haber al menos 1 adulto");
        Assert.isTrue(!date.isBefore(LocalDate.now()), "La fecha no puede ser anterior a hoy");

        var base  = store.getBaseSlots(branchId);       // ["20:00","20:30",...]
        var taken = store.getTakenSlots(branchId, date); // p.ej. ["21:00"]
        List<String> free = base.stream().filter(s -> !taken.contains(s)).toList();

        return new AvailabilityResponse(branchId, date, adults, kids, free);
    }
}
