package ubp.das.ristorinobackend.repository;

import ubp.das.ristorinobackend.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Validar que el correo no esté registrado (Req. 1)
    boolean existsByCorreo(String correo);

    // Login/Autenticación (Req. 2)
    Optional<Cliente> findByCorreo(String correo);
}