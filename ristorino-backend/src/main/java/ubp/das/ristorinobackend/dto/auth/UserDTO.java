package ubp.das.ristorinobackend.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String name;
}