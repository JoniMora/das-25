package ubp.das.ristorinobackend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistroRequest {

    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "El formato del correo es inválido.")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria.")
    private String password;

    private String nombre;
    private String apellido;
    private String telefono;
}