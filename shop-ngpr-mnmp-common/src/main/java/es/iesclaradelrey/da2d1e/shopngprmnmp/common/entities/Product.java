package es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 13)
    private String productCode;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, length = 4000)
    private String description;

    @Column(length = 500)
    private String image;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer discount;

    //relacion con marca: muchos productos de una marca
    @ManyToOne (optional = false)
    @JoinColumn(name = "brand_id", nullable = false) //ha de pertenecer a una marca si o si
    private Marca marca;

    //relacion con categorias: muchos productos
    @ManyToMany
    @JoinTable(name = "product_categories", // Nombre de la tabla intermediaria
    joinColumns = @JoinColumn(name = "product_id"), //clave de la entidad de id producto
    inverseJoinColumns = @JoinColumn(name = "category_id") //clave de la otra entidad, o sea de id categoria
    )
    private List<Categoria> categories;
}
