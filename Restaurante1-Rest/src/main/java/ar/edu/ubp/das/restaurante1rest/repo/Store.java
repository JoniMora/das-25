package ar.edu.ubp.das.restaurante1rest.repo;

import ar.edu.ubp.das.restaurante1rest.dto.BranchDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public interface Store  {
    List<BranchDto> listEnabledBranches();
    List<String> getBaseSlots(int branchId);
    Set<String> getTakenSlots(int branchId, LocalDate date);

    long insertReservation(int branchId, LocalDate date, LocalTime time,
                           int adults, int kids, int clientId, Integer zoneId);

    int cancelReservation(long codReserva);
}
