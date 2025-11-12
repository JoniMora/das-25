package ubp.das.ristorinobackend.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.context.SecurityContextHolder;
import ubp.das.ristorinobackend.dto.promotion.ClickRequest;
import ubp.das.ristorinobackend.dto.promotion.PromocionDTO;
import ubp.das.ristorinobackend.entity.Cliente;
import ubp.das.ristorinobackend.entity.ContenidoRestaurante;
import ubp.das.ristorinobackend.repository.ClienteRepository;
import ubp.das.ristorinobackend.repository.ContenidoClickRepository;
import ubp.das.ristorinobackend.repository.ContenidoRestauranteRepository;
import org.springframework.stereotype.Service;
import ubp.das.ristorinobackend.entity.ContenidoClick;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PromocionService {

    private final ContenidoRestauranteRepository repository;
    private final ContenidoClickRepository clickRepository;
    private final ClienteRepository clienteRepository;
    public PromocionService(ContenidoRestauranteRepository repository,ContenidoClickRepository clickRepository,
                            ClienteRepository clienteRepository ) {
        this.repository = repository;
        this.clickRepository = clickRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<PromocionDTO> obtenerPromocionesActivas() {
        return repository.findActivePromotions()
                .stream()
                .map(PromocionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<PromocionDTO> obtenerPromocionesActivasPorRestaurante(Long restauranteId) {
        return repository.findActivePromotionsByRestauranteId(restauranteId)
                .stream()
                .map(PromocionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void registrarClick(ClickRequest request) {
        ContenidoRestaurante contenido = repository.findByCodContenidoRestaurante(request.getCodContenido())
                .orElseThrow(() -> new RuntimeException("Contenido no encontrado"));
        ContenidoClick click = new ContenidoClick();
        click.setContenido(contenido);
        click.setFechaHoraClick(LocalDateTime.now());
        click.setCostoClickLiquidar(contenido.getCostoClick());
        click.setNotificado(false);

        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof User) {
                String correo = ((User) principal).getUsername();
                Optional<Cliente> clienteOpt = clienteRepository.findByCorreo(correo);
                clienteOpt.ifPresent(click::setCliente);
            }
        } catch (Exception e) {}
        clickRepository.save(click);
    }
}