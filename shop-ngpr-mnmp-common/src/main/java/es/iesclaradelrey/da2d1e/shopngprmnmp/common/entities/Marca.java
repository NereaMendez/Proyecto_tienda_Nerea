package es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "marcas")
public class Marca {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    //una marca tiene muchos productos
    @OneToMany(mappedBy = "marca")
    private List<Product> products;
}
