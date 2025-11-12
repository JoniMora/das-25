package ubp.das.ristorinobackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ubp.das.ristorinobackend.entity.ContenidoClick;
import java.util.List;

@Repository
public interface ContenidoClickRepository extends JpaRepository<ContenidoClick, Long> {

    @Query("SELECT c FROM ContenidoClick c WHERE c.notificado = false")
    List<ContenidoClick> findClicksPendientesDeNotificar();
}
