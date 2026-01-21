package ar.edu.ubp.das.restaurante2soap.services;

public class ConsultarDispResponseDTO {
    private boolean disponible;
    private String mensaje;

    public ConsultarDispResponseDTO() {}

    public ConsultarDispResponseDTO(boolean disponible, String mensaje) {
        this.disponible = disponible;
        this.mensaje = mensaje;
    }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
