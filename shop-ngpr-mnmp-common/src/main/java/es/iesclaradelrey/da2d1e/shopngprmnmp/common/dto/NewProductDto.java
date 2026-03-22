package es.iesclaradelrey.da2d1e.shopngprmnmp.common.dto;

import lombok.*;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewProductDto {
    private String productCode;
    private String name;
    private String description;
    private String image;
    private Double price;
    private Integer discount;
    private Long marcaId;
    private Set<Long> categoryIds;
}