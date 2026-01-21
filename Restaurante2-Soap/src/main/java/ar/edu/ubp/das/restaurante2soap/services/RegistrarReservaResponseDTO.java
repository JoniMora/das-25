package ar.edu.ubp.das.restaurante2soap.services;

public class RegistrarReservaResponseDTO {
    private String codigoReserva;
    private String estado;

    public RegistrarReservaResponseDTO() {}

    public RegistrarReservaResponseDTO(String codigoReserva, String estado) {
        this.codigoReserva = codigoReserva;
        this.estado = estado;
    }

    public String getCodigoReserva() { return codigoReserva; }
    public void setCodigoReserva(String codigoReserva) { this.codigoReserva = codigoReserva; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
