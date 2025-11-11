package ubp.das.ristorinobackend.repository;

import ubp.das.ristorinobackend.entity.ContenidoRestaurante;
import ubp.das.ristorinobackend.entity.ContenidoRestauranteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface ContenidoRestauranteRepository extends JpaRepository<ContenidoRestaurante, ContenidoRestauranteId> {

     // Busca TODAS las promociones donde la fecha actual esté entre la fecha de inicio y fin. (Req. 12)
    @Query("SELECT c FROM ContenidoRestaurante c " +
            "WHERE c.fechaIniVigencia <= CURRENT_DATE " +
            "AND (c.fechaFinVigencia IS NULL OR c.fechaFinVigencia >= CURRENT_DATE)")
    List<ContenidoRestaurante> findActivePromotions();

    // Busca promociones activas (por fecha) Y filtradas por un restaurante específico. (Req. 11)
    @Query("SELECT c FROM ContenidoRestaurante c " +
            "WHERE c.restaurante.nroRestaurante = :restauranteId " +
            "AND c.fechaIniVigencia <= CURRENT_DATE " +
            "AND (c.fechaFinVigencia IS NULL OR c.fechaFinVigencia >= CURRENT_DATE)")
    List<ContenidoRestaurante> findActivePromotionsByRestauranteId(Long restauranteId);

    // Busca una promo por su ID de negocio
    Optional<ContenidoRestaurante> findByCodContenidoRestaurante(String codContenidoRestaurante);
}