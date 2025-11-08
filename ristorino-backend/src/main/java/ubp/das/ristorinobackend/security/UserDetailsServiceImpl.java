package ubp.das.ristorinobackend.security;

import ubp.das.ristorinobackend.entity.Cliente;
import ubp.das.ristorinobackend.repository.ClienteRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    public UserDetailsServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente no encontrado con correo: " + correo));

        return new org.springframework.security.core.userdetails.User(
                cliente.getCorreo(),
                cliente.getPasswordHash(),
                new ArrayList<>() // Lista de Roles/Autoridades
        );
    }
}