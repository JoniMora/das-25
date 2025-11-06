package ubp.das.ristorinobackend.repository;

import ubp.das.ristorinobackend.entity.ContenidoRestaurante;
import ubp.das.ristorinobackend.entity.ContenidoRestauranteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContenidoRestauranteRepository extends JpaRepository<ContenidoRestaurante, ContenidoRestauranteId> {
    // Si fecha_fin_vigencia es NULL, se considera siempre activa.
    @Query("SELECT c FROM ContenidoRestaurante c " +
            "WHERE c.fechaIniVigencia <= CURRENT_DATE " +
            "AND (c.fechaFinVigencia IS NULL OR c.fechaFinVigencia >= CURRENT_DATE)")
    List<ContenidoRestaurante> findActivePromotions();
}