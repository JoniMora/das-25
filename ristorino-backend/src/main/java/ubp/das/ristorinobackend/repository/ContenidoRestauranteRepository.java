package ubp.das.ristorinobackend.repository;

import ubp.das.ristorinobackend.entity.ContenidoRestaurante;
import ubp.das.ristorinobackend.entity.ContenidoRestauranteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContenidoRestauranteRepository extends JpaRepository<ContenidoRestaurante, ContenidoRestauranteId> {

    // Promos activas (por fecha)
    @Query("SELECT c FROM ContenidoRestaurante c " +
        "WHERE c.fechaIniVigencia <= CURRENT_DATE " +
        "AND (c.fechaFinVigencia IS NULL OR c.fechaFinVigencia >= CURRENT_DATE)")
    List<ContenidoRestaurante> findActivePromotions();

    // Promos activas de un restaurante
    @Query("SELECT c FROM ContenidoRestaurante c " +
        "WHERE c.restaurante.nroRestaurante = :restauranteId " +
        "AND c.fechaIniVigencia <= CURRENT_DATE " +
        "AND (c.fechaFinVigencia IS NULL OR c.fechaFinVigencia >= CURRENT_DATE)")
    List<ContenidoRestaurante> findActivePromotionsByRestauranteId(Long restauranteId);

    // Buscar por código de negocio
    Optional<ContenidoRestaurante> findByCodContenidoRestaurante(String codContenidoRestaurante);
}
