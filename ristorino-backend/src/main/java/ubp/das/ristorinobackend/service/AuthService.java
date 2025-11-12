package ubp.das.ristorinobackend.service;

import org.springframework.transaction.annotation.Transactional;
import ubp.das.ristorinobackend.dto.auth.RegistroRequest;
import ubp.das.ristorinobackend.dto.auth.LoginRequest;
import ubp.das.ristorinobackend.entity.Cliente;
import ubp.das.ristorinobackend.repository.ClienteRepository;
import ubp.das.ristorinobackend.security.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }
    @Transactional(rollbackFor = Exception.class)
    public Cliente registrarCliente(RegistroRequest request) {
        if (clienteRepository.existsByCorreo(request.getCorreo())) {
            throw new IllegalArgumentException("El correo ya se encuentra registrado.");
        }
        // Tratamos y limpiamos las cadenas de texto
        String cleanPassword = request.getPassword().trim();

        Cliente cliente = new Cliente();
        cliente.setCorreo(request.getCorreo().trim());
        //cliente.setCorreo(request.getCorreo());
        /*Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getCorreo(),
                        request.getPassword()
                )
        );*/
        //cliente.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        // 1. CODIFICACIÓN
        cliente.setPasswordHash(passwordEncoder.encode(cleanPassword));

        // 2. LLAVE FORÁNEA Y CAMPOS OBLIGATORIOS (Solución al 500)
        cliente.setNroLocalidad("CBA-CAP");
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setTelefono(request.getTelefono());
        return clienteRepository.save(cliente);
    }

    public String autenticarCliente(LoginRequest request) {
        // Usamos .trim() para el login también, por seguridad
        String cleanCorreo = request.getCorreo().trim();
        String cleanPassword = request.getPassword().trim();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        //request.getCorreo(),
                        //request.getPassword()
                        cleanCorreo, // <-- USA LA VARIABLE LIMPIA
                        cleanPassword // <-- USA LA VARIABLE LIMPIA
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }
}