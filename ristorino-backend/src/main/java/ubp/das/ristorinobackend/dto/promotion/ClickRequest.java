package ubp.das.ristorinobackend.dto.promotion;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClickRequest {
    @NotBlank
    private String codContenido;
}
