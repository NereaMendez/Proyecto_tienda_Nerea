package es.iesclaradelrey.da2d1e.shopngprmnmp.common.mappers;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Marca;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.models.NewMarcaModel;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.models.NewProductModel;

public class MarcaMapper {

    private MarcaMapper() {}

    public static NewMarcaModel map (Marca marca) {
        return NewMarcaModel.builder()
                .name(marca.getName())
                .products(marca.getProducts())
                .build();

    }
    public static Marca map (NewMarcaModel newMarcaModel) {
        return Marca.builder()
                .name(newMarcaModel.getName())
                .products(newMarcaModel.getProducts())
                .build();
    }
}
