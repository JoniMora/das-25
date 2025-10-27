package ar.edu.ubp.das.restaurante1rest.repo;

import ar.edu.ubp.das.restaurante1rest.dto.BranchDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
        // En tu modelo NO existen s.ciudad ni s.habilitado.
        // Tomamos "city" de localidades o, si no hubiera, del barrio de la sucursal.
        final String sql = """
            SELECT 
                s.nro_sucursal AS id,
                s.nom_sucursal AS name,
                COALESCE(l.nom_localidad, s.barrio) AS city,
                CAST(1 AS bit) AS enabled   -- si luego agregás columna 'habilitado', reemplazá esto
            FROM dbo.sucursales s
            LEFT JOIN dbo.localidades l ON l.nro_localidad = s.nro_localidad
            ORDER BY s.nro_sucursal
            """;
        return jdbc.query(sql, (rs, i) -> mapBranch(rs));
    }

    private BranchDto mapBranch(ResultSet rs) throws SQLException {
        return new BranchDto(
                rs.getInt("id"),         // tu BranchDto actual parece usar int; si fuera String, cambiá aquí
                rs.getString("name"),
                rs.getString("city"),
                rs.getBoolean("enabled")
        );
    }

    // ===================== SLOTS BASE =====================
    @Override
    public List<String> getBaseSlots(int branchId) {
        // Usamos TU función dbo.fn_slots_discretos(rest, suc, step).
        // Como solo pasás nro_sucursal (branchId), obtenemos nro_restaurante por subquery.
        final String sql = """
            SELECT hhmm
            FROM dbo.fn_slots_discretos(
                (SELECT TOP 1 s.nro_restaurante FROM dbo.sucursales s WHERE s.nro_sucursal = ?),
                ?, 
                30
            )
            ORDER BY hhmm
            """;
        return jdbc.query(sql, ps -> {
            ps.setInt(1, branchId); // nro_restaurante (subquery)
            ps.setInt(2, branchId); // nro_sucursal
        }, (rs, i) -> rs.getString("hhmm"));
    }

    // ===================== SLOTS TOMADOS =====================
    @Override
    public Set<String> getTakenSlots(int branchId, LocalDate date) {
        // Tu tabla real es reservas_sucursales, y la ocupación es cancelada = 0
        final String sql = """
            SELECT CONVERT(char(5), r.hora_reserva, 108) AS hhmm
            FROM dbo.reservas_sucursales r
            WHERE r.nro_restaurante = (SELECT TOP 1 s.nro_restaurante 
                                       FROM dbo.sucursales s 
                                       WHERE s.nro_sucursal = ?)
              AND r.nro_sucursal    = ?
              AND r.fecha_reserva   = ?
              AND r.cancelada       = 0
            """;
        List<String> list = jdbc.query(sql, ps -> {
            ps.setInt(1, branchId);    // subquery: nro_restaurante
            ps.setInt(2, branchId);    // nro_sucursal
            ps.setObject(3, date);     // fecha_reserva
        }, (rs, i) -> rs.getString("hhmm"));
        return new HashSet<>(list);
    }
}

