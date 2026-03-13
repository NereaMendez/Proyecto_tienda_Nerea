package es.iesclaradelrey.da2d1e.shopngprmnmp.common.mappers;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.models.NewProductModel;

public class ProductMapper {

    private ProductMapper() {}

    public static NewProductModel map (Product product) {
        return NewProductModel.builder()
                .productCode(product.getProductCode())
                .name(product.getName())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .discount(product.getDiscount())
                //Extraemos solo el ID de la marca
                .marcaId(product.getMarca() != null ? product.getMarca().getId() : null)
                //Convertimos la lista de entidades Categoria a una lista de IDs (Long)
                .categoryIds(product.getCategories().stream().map(c -> c.getId()).toList())
                .build();

    }
    public static Product map (NewProductModel newProductModel) {
        return Product.builder()
                .productCode(newProductModel.getProductCode())
                .name(newProductModel.getName())
                .description(newProductModel.getDescription())
                .image(newProductModel.getImage())
                .price(newProductModel.getPrice())
                .discount(newProductModel.getDiscount())

                .build();
    }
}

