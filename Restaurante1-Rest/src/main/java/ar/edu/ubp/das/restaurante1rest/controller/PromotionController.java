package ar.edu.ubp.das.restaurante1rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/promotions")
public class PromotionController {
    private final JdbcTemplate jdbc;
    public PromotionController(JdbcTemplate jdbc) { this.jdbc = jdbc;}
    /* obtener promociones para angular*/
    @GetMapping
    public List<PromotionDto> getPromotions() {
        String sql = """
            SELECT 
                c.nro_contenido as id,
                c.contenido_a_publicar as title,
                c.imagen_a_publicar as imageUrl,
                r.razon_social as restaurantName,
                s.nom_sucursal as branchName,
                c.costo_click as costPerClick
            FROM dbo.contenidos c
            JOIN dbo.restaurantes r ON c.nro_restaurante = r.nro_restaurante
            LEFT JOIN dbo.sucursales s ON c.nro_sucursal = s.nro_sucursal 
                                     AND c.nro_restaurante = s.nro_restaurante
            WHERE c.publicado = 1
            """;
        return jdbc.query(sql, (rs, rowNum) ->
                new PromotionDto(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("title"),
                        rs.getString("restaurantName"),
                        rs.getString("branchName"),
                        rs.getString("imageUrl"),
                        rs.getBigDecimal("costPerClick")
                ));
    }
    @PostMapping("/click")
    public ResponseEntity<Map<String, String>> registerClick(@RequestBody PromotionClickRequest request) {
        System.out.println("CLIC REGISTRADO - La Bella Pizza - Promo: " + request.promotionId());
        try {
            String sql = """
            INSERT INTO dbo.clicks_contenidos 
            (nro_restaurante, nro_contenido, nro_cliente, costo_click)
            VALUES (?, ?, NULL, 0.50)
            """;
            jdbc.update(sql,
                    1,
                    Integer.parseInt(request.promotionId())
            );
        } catch (Exception e) {
            System.out.println(" Error al guardar click en BD: " + e.getMessage());
        }
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Click registrado y contabilizado",
                "timestamp", LocalDateTime.now().toString()
        ));
    }
    public record PromotionDto(
            String id,
            String title,
            String restaurantName,
            String branchName,
            String imageUrl,
            BigDecimal costPerClick
    ) {}

    public record PromotionClickRequest(
            String promotionId,
            String userId,
            LocalDateTime clickDate
    ) {}
}

