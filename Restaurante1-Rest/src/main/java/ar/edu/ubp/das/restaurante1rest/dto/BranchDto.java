package ar.edu.ubp.das.restaurante1rest.dto;

public record BranchDto(
    int id,
    String name,
    String city,
    boolean enabled
) {}
