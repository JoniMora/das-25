package ar.edu.ubp.das.restaurante1rest.service;

import ar.edu.ubp.das.restaurante1rest.dto.BranchDto;
import ar.edu.ubp.das.restaurante1rest.repo.Store;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService {
    private final Store store;
    public BranchService(Store store) { this.store = store; }
    public List<BranchDto> list() { return store.listEnabledBranches(); }
}

