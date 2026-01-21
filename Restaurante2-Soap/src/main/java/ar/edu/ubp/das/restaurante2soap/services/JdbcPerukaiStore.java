package ar.edu.ubp.das.restaurante2soap.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcPerukaiStore implements PerukaiStore {

    private final JdbcTemplate jdbc;

    public JdbcPerukaiStore(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<SucursalDTO> obtenerSucursales() {
        final String sql = """
            SELECT id_sucursal, nombre, barrio, direccion, capacidad, habilitada
            FROM dbo.sucursales
            ORDER BY id_sucursal
        """;

        return jdbc.query(sql, (rs, i) -> new SucursalDTO(
                rs.getInt("id_sucursal"),
                rs.getString("nombre"),
                rs.getString("barrio"),
                rs.getString("direccion"),
                rs.getObject("capacidad") == null ? null : rs.getInt("capacidad"),
                rs.getBoolean("habilitada")
        ));
    }

    @Override
    public boolean existeSucursalHabilitada(int sucursalId) {
        final String sql = """
            SELECT COUNT(1)
            FROM dbo.sucursales
            WHERE id_sucursal = ?
              AND habilitada = 1
        """;
        Integer n = jdbc.queryForObject(sql, Integer.class, sucursalId);
        return n != null && n > 0;
    }

    @Override
    public boolean slotDisponible(int sucursalId, String fechaIso, String horaHHmm) {
        final String sql = """
            SELECT COUNT(1)
            FROM dbo.reservas
            WHERE id_sucursal = ?
              AND fecha = ?
              AND hora = ?
              AND cancelada = 0
        """;
        Integer n = jdbc.queryForObject(sql, Integer.class, sucursalId, fechaIso, horaHHmm);
        return n != null && n == 0;
    }

    @Override
    public String registrarReserva(int sucursalId, String fechaIso, String horaHHmm, int comensales,
                                   String clienteNombre, String clienteEmail) {

        final String sql = """
            DECLARE @cod VARCHAR(40) =
              CONCAT('PERU-', FORMAT(GETDATE(),'yyyyMMddHHmmss'), '-', ABS(CHECKSUM(NEWID())) % 10000);

            IF EXISTS (
              SELECT 1
              FROM dbo.reservas WITH (UPDLOCK, HOLDLOCK)
              WHERE id_sucursal = ?
                AND fecha = ?
                AND hora = ?
                AND cancelada = 0
            )
            BEGIN
              RAISERROR('Slot ocupado', 16, 1);
              RETURN;
            END

            INSERT INTO dbo.reservas
              (cod_reserva, id_sucursal, fecha, hora, comensales, cliente_nombre, cliente_email, cancelada)
            VALUES
              (@cod, ?, ?, ?, ?, ?, ?, 0);

            SELECT @cod AS cod;
        """;

        String cod = jdbc.query(sql, ps -> {
            ps.setInt(1, sucursalId);
            ps.setString(2, fechaIso);
            ps.setString(3, horaHHmm);

            ps.setInt(4, sucursalId);
            ps.setString(5, fechaIso);
            ps.setString(6, horaHHmm);
            ps.setInt(7, comensales);
            ps.setString(8, clienteNombre);
            ps.setString(9, clienteEmail);
        }, rs -> rs.next() ? rs.getString("cod") : null);

        if (cod == null) throw new IllegalStateException("No se obtuvo cod_reserva");
        return cod;
    }

    @Override
    public void guardarNotificacionClicks(String restauranteId, int totalClicks, String montoTotal) {
        final String sql = """
            INSERT INTO dbo.clicks_liquidacion(restaurante_id, total_clicks, monto_total, fecha_hora)
            VALUES (?, ?, ?, SYSUTCDATETIME())
        """;
        jdbc.update(sql, restauranteId, totalClicks, montoTotal);
    }
}

