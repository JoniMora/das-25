package ubp.das.ristorinobackend.repository;

import ubp.das.ristorinobackend.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    @Query("SELECT DISTINCT r FROM Restaurante r " +
            "LEFT JOIN FETCH r.sucursales " +
            "LEFT JOIN FETCH r.preferencias p " +
            "LEFT JOIN FETCH p.dominio d " +
            "LEFT JOIN FETCH d.categoria " +
            "WHERE r.nroRestaurante = :id")
    Optional<Restaurante> findByIdWithDetails(Long id);
}