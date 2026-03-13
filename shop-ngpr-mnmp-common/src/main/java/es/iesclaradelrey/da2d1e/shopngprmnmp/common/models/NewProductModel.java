package es.iesclaradelrey.da2d1e.shopngprmnmp.common.models;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Categoria;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Marca;
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
public class NewProductModel {

    private String productCode;

    private String name;

    private String description;

    private String image;

    private Double price;

    private Integer discount;

    private Long marcaId;

    private List<Long> categoryIds;

    private Set<String> modulesProduct = new HashSet<>();


}
