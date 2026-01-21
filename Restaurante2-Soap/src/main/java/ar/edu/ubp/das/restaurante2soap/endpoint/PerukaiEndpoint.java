package ar.edu.ubp.das.restaurante2soap.endpoint;

import ar.edu.ubp.das.restaurante2soap.services.*;
import ar.edu.ubp.das.restaurante2soap.ws.*;
import org.springframework.ws.server.endpoint.annotation.*;

import java.math.BigDecimal;

@Endpoint
public class PerukaiEndpoint {

    private static final String NAMESPACE_URI = "http://services.perukai.das.ubp.edu.ar/";
    private final PerukaiWS perukaiService;

    public PerukaiEndpoint(PerukaiWS perukaiService) {
        this.perukaiService = perukaiService;
    }

    // ===================== Req 45: Obtener sucursales =====================
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "obtenerSucursalesRequest")
    @ResponsePayload
    public ObtenerSucursalesResponse obtenerSucursales(@RequestPayload ObtenerSucursalesRequest request) {

        var list = perukaiService.obtenerSucursales();

        ObtenerSucursalesResponse response = new ObtenerSucursalesResponse();
        for (SucursalDTO s : list) {
            Sucursal x = new Sucursal();
            x.setId(s.getId());
            x.setNombre(s.getNombre());
            x.setBarrio(s.getBarrio());
            x.setDireccion(s.getDireccion());
            x.setCapacidad(s.getCapacidad());
            x.setHabilitada(s.isHabilitada());
            response.getSucursales().add(x);
        }
        return response;
    }

    // ===================== Req 37: Consultar disponibilidad =====================
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "consultarDisponibilidadRequest")
    @ResponsePayload
    public ConsultarDisponibilidadResponse consultarDisponibilidad(
            @RequestPayload ConsultarDisponibilidadRequest request) {

        ConsultarDispRequestDTO dto = new ConsultarDispRequestDTO(
                request.getSucursal(),
                request.getFecha(),
                request.getHora(),
                request.getComensales()
        );

        ConsultarDispResponseDTO resDto = perukaiService.consultarDisponibilidad(dto);

        ConsultarDisponibilidadResponse response = new ConsultarDisponibilidadResponse();
        response.setDisponible(resDto.isDisponible());
        response.setMensaje(resDto.getMensaje());
        return response;
    }

    // ===================== Req 38: Registrar reserva =====================
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registrarReservaRequest")
    @ResponsePayload
    public RegistrarReservaResponse registrarReserva(@RequestPayload RegistrarReservaRequest request) {

        RegistrarReservaRequestDTO dto = new RegistrarReservaRequestDTO(
                request.getSucursal(),
                request.getFecha(),
                request.getHora(),
                request.getComensales(),
                request.getClienteNombre(),
                request.getClienteEmail()
        );

        RegistrarReservaResponseDTO resDto = perukaiService.registrarReserva(dto);

        RegistrarReservaResponse response = new RegistrarReservaResponse();
        response.setCodigoReserva(resDto.getCodigoReserva());
        response.setEstado(resDto.getEstado());
        return response;
    }

    // ===================== Batch monetización: notificar clicks =====================
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "notificarClicksRequest")
    @ResponsePayload
    public NotificarClicksResponse notificarClicks(@RequestPayload NotificarClicksRequest request) {

        ClickNotificationDTO dto = new ClickNotificationDTO(
                request.getRestauranteId(),
                request.getTotalClicks(),
                request.getMontoTotal() == null ? BigDecimal.ZERO : request.getMontoTotal()
        );

        ClickNotificationResponseDTO resDto = perukaiService.notificarClicks(dto);

        NotificarClicksResponse response = new NotificarClicksResponse();
        response.setEstado(resDto.getEstado());
        response.setMensaje(resDto.getMensaje());
        return response;
    }

    // ===================== Ping =====================
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "pingRequest")
    @ResponsePayload
    public PingResponse ping(@RequestPayload PingRequest request) {
        PingResponse response = new PingResponse();
        response.setReturn(perukaiService.ping());
        return response;
    }
}


