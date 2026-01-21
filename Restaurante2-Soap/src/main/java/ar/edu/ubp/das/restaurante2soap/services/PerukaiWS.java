package ar.edu.ubp.das.restaurante2soap.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PerukaiWS {

    private final PerukaiStore store;

    public PerukaiWS(PerukaiStore store) {
        this.store = store;
    }

    public String ping() {
        return "Perukai SOAP Service - OK";
    }

    public java.util.List<SucursalDTO> obtenerSucursales() {
        return store.obtenerSucursales();
    }

    public ConsultarDispResponseDTO consultarDisponibilidad(ConsultarDispRequestDTO req) {

        if (req.getComensales() < 1) {
            return new ConsultarDispResponseDTO(false, "Debe haber al menos 1 comensal");
        }

        // validación simple de fecha (no pasada)
        try {
            LocalDate d = LocalDate.parse(req.getFecha());
            if (d.isBefore(LocalDate.now())) {
                return new ConsultarDispResponseDTO(false, "La fecha no puede ser anterior a hoy");
            }
        } catch (Exception e) {
            return new ConsultarDispResponseDTO(false, "Fecha inválida (use YYYY-MM-DD)");
        }

        int sucursalId = req.getSucursal();
        if (!store.existeSucursalHabilitada(sucursalId)) {
            return new ConsultarDispResponseDTO(false, "Sucursal inexistente o no habilitada");
        }

        boolean ok = store.slotDisponible(sucursalId, req.getFecha(), req.getHora());
        return new ConsultarDispResponseDTO(ok, ok ? "Disponible" : "No disponible");
    }

    public RegistrarReservaResponseDTO registrarReserva(RegistrarReservaRequestDTO req) {

        ConsultarDispRequestDTO disp = new ConsultarDispRequestDTO(
                req.getSucursal(), req.getFecha(), req.getHora(), req.getComensales()
        );

        ConsultarDispResponseDTO dispRes = consultarDisponibilidad(disp);
        if (!dispRes.isDisponible()) {
            return new RegistrarReservaResponseDTO(null, "RECHAZADA: " + dispRes.getMensaje());
        }

        String cod = store.registrarReserva(
                req.getSucursal(),
                req.getFecha(),
                req.getHora(),
                req.getComensales(),
                req.getClienteNombre(),
                req.getClienteEmail()
        );

        return new RegistrarReservaResponseDTO(cod, "CONFIRMADA");
    }

    public ClickNotificationResponseDTO notificarClicks(ClickNotificationDTO dto) {
        store.guardarNotificacionClicks(
                dto.getRestauranteId(),
                dto.getTotalClicks(),
                dto.getMontoTotal().toPlainString()
        );
        return new ClickNotificationResponseDTO("OK", "Liquidación de clicks guardada en Perukai");
    }
}


