package es.iesclaradelrey.da2d1e.shopngprmnmp.common.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {
    private String name;
    private String description;
    private String image;
}