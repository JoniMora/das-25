package ar.edu.ubp.das.restaurante1rest.controller;

import ar.edu.ubp.das.restaurante1rest.dto.BranchDto;
import ar.edu.ubp.das.restaurante1rest.service.BranchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/branches")
public class BranchController {
    private final BranchService service;
    public BranchController(BranchService service) { this.service = service; }

    @GetMapping
    public List<BranchDto> list() { return service.list(); }
}
