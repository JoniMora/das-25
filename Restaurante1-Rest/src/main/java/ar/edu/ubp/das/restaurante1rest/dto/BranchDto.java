package ar.edu.ubp.das.restaurante1rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {
    private int id;
    private String name;
    private String city;
    private boolean enabled;
}
