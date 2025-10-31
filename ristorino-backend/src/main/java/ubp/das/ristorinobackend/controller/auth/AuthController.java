package ubp.das.ristorinobackend.controller.auth;

import ubp.das.ristorinobackend.dto.auth.RegistroRequest;
import ubp.das.ristorinobackend.dto.auth.LoginRequest;
import ubp.das.ristorinobackend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Req. 1: Dar de alta usuario
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistroRequest request) {
        try {
            authService.registrarCliente(request);

            return new ResponseEntity<>("Usuario registrado exitosamente.", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno durante el registro.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Req. 2: Iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            String jwtToken = authService.autenticarCliente(request);

            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            return new ResponseEntity<>("Credenciales inválidas.", HttpStatus.UNAUTHORIZED);
        }
    }
}