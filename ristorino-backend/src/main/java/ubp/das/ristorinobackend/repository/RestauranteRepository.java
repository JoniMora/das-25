package ubp.das.ristorinobackend.repository;

import ubp.das.ristorinobackend.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository toma la Entidad (Restaurante) y el tipo de su clave primaria (Long)
@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    // Spring Data JPA nos dará automáticamente findById(), findAll(), save(), etc.
}