package ubp.das.ristorinobackend.service;

import org.springframework.transaction.annotation.Transactional;
import ubp.das.ristorinobackend.dto.auth.LoginResponse;
import ubp.das.ristorinobackend.dto.auth.RegistroRequest;
import ubp.das.ristorinobackend.dto.auth.LoginRequest;
import ubp.das.ristorinobackend.dto.auth.UserDTO;
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

        public AuthService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager, JwtProvider jwtProvider)
        {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;}

        @Transactional(rollbackFor = Exception.class)
        public Cliente registrarCliente(RegistroRequest request)
        {
                if (clienteRepository.existsByCorreo(request.getCorreo())) {
                throw new IllegalArgumentException("El correo ya se encuentra registrado.");}
                String cleanCorreo = request.getCorreo().trim(); // evita duplicados por espacios
                Cliente cliente = new Cliente();
                cliente.setCorreo(cleanCorreo);
                cliente.setPasswordHash(passwordEncoder.encode(request.getPassword()));
                cliente.setNroLocalidad("CBA-CAP");//ver si no pasa nada si lo borras

                cliente.setNombre(request.getNombre());
                cliente.setApellido(request.getApellido());
                cliente.setTelefono(request.getTelefono());
                return clienteRepository.save(cliente);
        }

        public LoginResponse autenticarCliente(LoginRequest request)
        {
                String cleanCorreo = request.getCorreo().trim();
                String rawPassword = request.getPassword();

                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                cleanCorreo,
                                rawPassword
                        )
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwtToken = jwtProvider.generateToken(authentication);

                Cliente cliente = clienteRepository.findByCorreo(cleanCorreo)
                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado después de autenticación"));

                UserDTO userDto = UserDTO.builder()
                        .id(cliente.getNroCliente())
                        .email(cliente.getCorreo())
                        .name(cliente.getNombre() + " " + cliente.getApellido())
                        .build();

                return new LoginResponse(jwtToken, userDto);
        }
}
