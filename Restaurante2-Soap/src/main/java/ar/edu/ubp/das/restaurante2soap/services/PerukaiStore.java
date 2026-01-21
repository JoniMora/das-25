package ar.edu.ubp.das.restaurante2soap.services;

import java.util.List;

public interface PerukaiStore {
    List<SucursalDTO> obtenerSucursales();

    boolean existeSucursalHabilitada(int sucursalId);

    boolean slotDisponible(int sucursalId, String fechaIso, String horaHHmm);

    String registrarReserva(int sucursalId, String fechaIso, String horaHHmm, int comensales,
                            String clienteNombre, String clienteEmail);

    void guardarNotificacionClicks(String restauranteId, int totalClicks, String montoTotal);
}

