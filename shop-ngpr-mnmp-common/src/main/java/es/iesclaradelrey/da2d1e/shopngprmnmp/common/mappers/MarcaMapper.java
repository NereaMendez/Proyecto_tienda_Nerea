package es.iesclaradelrey.da2d1e.shopngprmnmp.common.mappers;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.dto.NewBrandDto;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Marca;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.models.NewMarcaModel;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.models.NewProductModel;

public class MarcaMapper {

    private MarcaMapper() {}

    /*
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
    } */

    public static NewBrandDto map(Marca marca) {
        if (marca == null) return null;

        return NewBrandDto.builder()
                .name(marca.getName())
                .build();
    }

    public static Marca map(NewBrandDto newBrandDto) {
        if (newBrandDto == null) return null;

        return Marca.builder()
                .name(newBrandDto.getName())
                .build();
    }

}
