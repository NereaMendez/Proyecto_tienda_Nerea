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

public class NewMarcaModel {

    private String name;

    private List<Product> products;

    private Set<String> modulesMarca = new HashSet<>();

}
