package ar.edu.ubp.das.restaurante1rest.service;

import ar.edu.ubp.das.restaurante1rest.dto.ReservationRequest;
import ar.edu.ubp.das.restaurante1rest.dto.ReservationResponse;
import ar.edu.ubp.das.restaurante1rest.repo.Store;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
@Service
public class ReservationService {
    private final Store store;
    private static final DateTimeFormatter D = DateTimeFormatter.ISO_DATE;
    private static final DateTimeFormatter T = DateTimeFormatter.ofPattern("HH:mm");

    public ReservationService(Store store){ this.store = store; }

    public ReservationResponse create(String branchId, ReservationRequest req){
        // ANTES: req.date()
        LocalDate date = LocalDate.parse(req.getDate(), D);
        if (date.isBefore(LocalDate.now()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha no puede ser anterior a hoy");

        // ANTES: req.adults()
        if (req.getAdults() < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe haber al menos 1 adulto");

        // ANTES: req.time()
        LocalTime time = LocalTime.parse(req.getTime(), T);

        var base = store.getBaseSlots(branchId);
        // ANTES: req.time()
        if (!base.contains(req.getTime()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horario fuera de turno");

        var taken = store.getTakenSlots(branchId, date);
        // ANTES: req.time()
        if (taken.contains(req.getTime()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Horario ocupado");

        // ANTES: req.adults(), req.kids(), req.clientId(), req.zoneId()
        long cod = store.insertReservation(
                branchId,
                date,
                time,
                req.getAdults(),
                req.getKids(),
                req.getClientId(),
                req.getZoneId()
        );
        return new ReservationResponse(cod, branchId, date.format(D), time.format(T));
    }

    public void cancel(long codReserva){
        int n = store.cancelReservation(codReserva);
        if (n == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva inexistente");
    }
}
