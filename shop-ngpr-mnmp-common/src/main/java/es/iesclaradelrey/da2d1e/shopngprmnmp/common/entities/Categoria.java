package es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities;


import lombok.*;

import java.util.List;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(length = 2000)
    private String description;

    @Column(length = 500)
    private String image;

    //una categoria tiene muchos productos, one to many
    //mapped by = nombre del campo en la clase product
    @ManyToMany (mappedBy = "categories")
    private List<Product> products;

}