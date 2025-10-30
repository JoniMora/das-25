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

    public ReservationResponse create(int branchId, ReservationRequest req){
        LocalDate date = LocalDate.parse(req.date(), D);
        if (date.isBefore(LocalDate.now()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha no puede ser anterior a hoy");

        if (req.adults() < 1)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe haber al menos 1 adulto");

        LocalTime time = LocalTime.parse(req.time(), T);

        var base = store.getBaseSlots(branchId);
        if (!base.contains(req.time()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Horario fuera de turno");

        var taken = store.getTakenSlots(branchId, date);
        if (taken.contains(req.time()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Horario ocupado");

        long cod = store.insertReservation(branchId, date, time, req.adults(), req.kids(), req.clientId(), req.zoneId());
        return new ReservationResponse(cod, branchId, date.format(D), time.format(T));
    }

    public void cancel(long codReserva){
        int n = store.cancelReservation(codReserva);
        if (n == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva inexistente");
    }
}
