package ubp.das.ristorinobackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias_preferencias")
@Data
@NoArgsConstructor
public class CategoriaPreferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_categoria")
    private Integer codCategoria;

    @Column(name = "nom_categoria", nullable = false)
    private String nomCategoria;
}