package ar.edu.ubp.das.restaurante1rest.repo;

import ar.edu.ubp.das.restaurante1rest.dto.BranchDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface Store  {
    List<BranchDto> listEnabledBranches();
    List<String> getBaseSlots(int branchId);
    Set<String> getTakenSlots(int branchId, LocalDate date);
}
