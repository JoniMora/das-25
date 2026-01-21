package ar.edu.ubp.das.restaurante2soap.services;

public class RegistrarReservaRequestDTO {
    private int sucursal;          // <-- era String
    private String fecha;
    private String hora;
    private int comensales;
    private String clienteNombre;
    private String clienteEmail;

    public RegistrarReservaRequestDTO() {}

    public RegistrarReservaRequestDTO(int sucursal, String fecha, String hora,
                                      int comensales, String clienteNombre, String clienteEmail) {
        this.sucursal = sucursal;
        this.fecha = fecha;
        this.hora = hora;
        this.comensales = comensales;
        this.clienteNombre = clienteNombre;
        this.clienteEmail = clienteEmail;
    }

    public int getSucursal() { return sucursal; }
    public void setSucursal(int sucursal) { this.sucursal = sucursal; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public int getComensales() { return comensales; }
    public void setComensales(int comensales) { this.comensales = comensales; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public String getClienteEmail() { return clienteEmail; }
    public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }
}

