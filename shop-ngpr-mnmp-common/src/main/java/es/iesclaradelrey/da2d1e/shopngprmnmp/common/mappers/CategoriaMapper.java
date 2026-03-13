package es.iesclaradelrey.da2d1e.shopngprmnmp.common.mappers;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Categoria;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.models.NewCategoriaModel;

public class CategoriaMapper {
    private CategoriaMapper() {}

    public static NewCategoriaModel map (Categoria categoria) {
        return NewCategoriaModel.builder()
                .name(categoria.getName())
                .description(categoria.getDescription())
                .image(categoria.getImage())
                .products(categoria.getProducts())
                .build();

    }
    public static Categoria map (NewCategoriaModel newCategoriaModel) {
        return Categoria.builder()
                .name(newCategoriaModel.getName())
                .products(newCategoriaModel.getProducts())
                .build();
    }
}
