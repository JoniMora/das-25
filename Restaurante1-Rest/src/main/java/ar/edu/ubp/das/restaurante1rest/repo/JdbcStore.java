package ar.edu.ubp.das.restaurante1rest.repo;

import ar.edu.ubp.das.restaurante1rest.dto.BranchDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Repository
public class JdbcStore implements Store {

    private final JdbcTemplate jdbc;

    public JdbcStore(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // ===================== SUCURSALES =====================
    @Override
    public List<BranchDto> listEnabledBranches() {

        final String sql = """
            SELECT 
              s.nro_sucursal AS id,
              s.nom_sucursal AS name,
              COALESCE(l.nom_localidad, s.barrio) AS city,
              CAST(1 AS bit) AS enabled
            FROM dbo.sucursales s
            LEFT JOIN dbo.localidades l ON l.nro_localidad = s.nro_localidad
            ORDER BY s.nro_sucursal
            """;
        return jdbc.query(sql, (rs, i) -> mapBranch(rs));
    }

    private BranchDto mapBranch(ResultSet rs) throws SQLException {
        return new BranchDto(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("city"),
                rs.getBoolean("enabled")
        );
    }

    // ===================== SLOTS BASE =====================
    @Override
    public List<String> getBaseSlots(String branchId) {

        final String sql = """
            SELECT s.hhmm
            FROM dbo.fn_slots_discretos(
                    (SELECT TOP 1 s2.nro_restaurante FROM dbo.sucursales s2 WHERE s2.nro_sucursal = ?),
                    ?, 
                    30
                 ) AS s
            ORDER BY s.hhmm
            """;
        return jdbc.query(sql, ps -> {
            ps.setString(1, branchId);
            ps.setString(2, branchId);
        }, (rs, i) -> rs.getString("hhmm"));
    }

    // ===================== SLOTS TOMADOS =====================
    @Override
    public Set<String> getTakenSlots(String branchId, LocalDate date) {
        final String sql = """
        SELECT CONVERT(char(5), r.hora_reserva, 108) AS hhmm
        FROM dbo.reservas_sucursales r
        WHERE r.nro_restaurante = (SELECT TOP 1 s.nro_restaurante FROM dbo.sucursales s WHERE s.nro_sucursal = ?)
          AND r.nro_sucursal    = ?
          AND r.fecha_reserva   = ?
          AND r.cancelada       = 0
        """;
        List<String> list = jdbc.query(sql, ps -> {
            ps.setString(1, branchId);
            ps.setString(2, branchId);
            ps.setDate(3, java.sql.Date.valueOf(date));
        }, (rs, i) -> rs.getString("hhmm"));
        return new HashSet<>(list);
    }

    // ===================== INSERT RESERVA =====================
    @Override
    public long insertReservation(String branchId, LocalDate date, LocalTime time,
                                  int adults, int kids, String clientId, String zoneId) {
        final String sql = """
        DECLARE @rest VARCHAR(10) = (
          SELECT TOP 1 s.nro_restaurante
          FROM dbo.sucursales s
          WHERE s.nro_sucursal = ?
        );
    
        -- Evitar doble toma del slot (CAST explícito a time(0))
        IF EXISTS (
          SELECT 1
          FROM dbo.reservas_sucursales WITH (UPDLOCK, HOLDLOCK)
          WHERE nro_restaurante = @rest
            AND nro_sucursal    = ?
            AND fecha_reserva   = ?
            AND hora_reserva    = CAST(? AS time(0))
            AND cancelada       = 0
        )
        BEGIN
          RAISERROR('Slot ocupado', 16, 1);
          RETURN;
        END
    
        INSERT INTO dbo.reservas_sucursales
          (nro_cliente, fecha_reserva, nro_restaurante, nro_sucursal, cod_zona,
           hora_reserva, cant_adultos, cant_menores, costo_reserva, cancelada)
        VALUES
          (?, ?, @rest, ?, ?, CAST(? AS time(0)), ?, ?, NULL, 0);
    
        SELECT CAST(SCOPE_IDENTITY() AS BIGINT) AS cod;
    """;

        Long cod = jdbc.query(sql, ps -> {
            ps.setString(1, branchId);
            ps.setString(2, branchId);
            ps.setDate(3, java.sql.Date.valueOf(date));
            ps.setString(4, time.toString());

            ps.setString(5, clientId);
            ps.setDate(6, java.sql.Date.valueOf(date));
            ps.setString(7, branchId);
            if (zoneId == null) ps.setNull(8, java.sql.Types.INTEGER);
            else                ps.setString(8, zoneId);
            ps.setString(9, time.toString());
            ps.setInt(10, adults);
            ps.setInt(11, kids);
        }, rs -> rs.next() ? rs.getLong("cod") : null);

        if (cod == null) throw new IllegalStateException("No se obtuvo cod_reserva");
        return cod;
    }

    // ===================== CANCELAR RESERVA =====================
    @Override
    public int cancelReservation(long codReserva) {
        final String sql = """
            UPDATE dbo.reservas_sucursales
               SET cancelada = 1,
                   fecha_cancelacion = SYSUTCDATETIME()
             WHERE cod_reserva = ?
               AND cancelada = 0
            """;
        return jdbc.update(sql, codReserva);
    }
}

