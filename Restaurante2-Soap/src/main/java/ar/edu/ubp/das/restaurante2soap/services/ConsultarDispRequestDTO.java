package ar.edu.ubp.das.restaurante2soap.services;

public class ConsultarDispRequestDTO {
    private int sucursal;          // <-- era String
    private String fecha;
    private String hora;
    private int comensales;

    public ConsultarDispRequestDTO() {}

    public ConsultarDispRequestDTO(int sucursal, String fecha, String hora, int comensales) {
        this.sucursal = sucursal;
        this.fecha = fecha;
        this.hora = hora;
        this.comensales = comensales;
    }

    public int getSucursal() { return sucursal; }
    public void setSucursal(int sucursal) { this.sucursal = sucursal; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public int getComensales() { return comensales; }
    public void setComensales(int comensales) { this.comensales = comensales; }
}

