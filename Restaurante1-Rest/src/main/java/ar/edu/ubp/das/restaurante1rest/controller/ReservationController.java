package ar.edu.ubp.das.restaurante1rest.controller;

import ar.edu.ubp.das.restaurante1rest.dto.ReservationRequest;
import ar.edu.ubp.das.restaurante1rest.dto.ReservationResponse;
import ar.edu.ubp.das.restaurante1rest.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ReservationController {
    private final ReservationService svc; // <-- FALTABA

    public ReservationController(ReservationService svc) { // <-- FALTABA
        this.svc = svc;
    }

    @PostMapping("/branches/{branchId}/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse create(@PathVariable int branchId, @RequestBody ReservationRequest req){
        return svc.create(branchId, req);
    }

    @DeleteMapping("/reservations/{codReserva}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable long codReserva){
        svc.cancel(codReserva);
    }
}
