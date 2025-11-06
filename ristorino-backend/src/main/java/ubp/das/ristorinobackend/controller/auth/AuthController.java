package ubp.das.ristorinobackend.controller.auth;

import lombok.RequiredArgsConstructor;
import ubp.das.ristorinobackend.dto.auth.RegistroRequest;
import ubp.das.ristorinobackend.dto.auth.LoginRequest;
import ubp.das.ristorinobackend.service.AuthService;
import ubp.das.ristorinobackend.security.JwtProvider;
import ubp.das.ristorinobackend.security.TokenBlacklistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final TokenBlacklistService blacklist;

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

    // Req. 5: Cerrar sesión
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Falta Authorization: Bearer <token>");
        }
        String token = authorization.substring(7);

        if (!jwtProvider.validateToken(token)) {
            return ResponseEntity.ok("Sesión cerrada (token ya inválido).");
        }

        long exp = jwtProvider.getExpirationEpochSeconds(token);
        blacklist.revoke(token, exp);

        return ResponseEntity.ok("Sesión cerrada.");
    }
}