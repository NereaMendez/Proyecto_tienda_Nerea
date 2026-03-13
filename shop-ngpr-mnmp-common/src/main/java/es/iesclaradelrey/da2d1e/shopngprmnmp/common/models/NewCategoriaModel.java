package es.iesclaradelrey.da2d1e.shopngprmnmp.common.models;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Product;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoriaModel {

    private String name;
    private String description;
    private String image;
    private List<Product> products;
    private Set<String> modulesCategoria = new HashSet<>();
}
