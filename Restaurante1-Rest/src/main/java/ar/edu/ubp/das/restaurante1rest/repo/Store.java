package ar.edu.ubp.das.restaurante1rest.repo;

import ar.edu.ubp.das.restaurante1rest.dto.BranchDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public interface Store  {
    List<BranchDto> listEnabledBranches();
    List<String> getBaseSlots(String branchId);
    Set<String> getTakenSlots(String branchId, LocalDate date);

    long insertReservation(String branchId, LocalDate date, LocalTime time,
                           int adults, int kids, String clientId, String zoneId);

    int cancelReservation(long codReserva);
}
