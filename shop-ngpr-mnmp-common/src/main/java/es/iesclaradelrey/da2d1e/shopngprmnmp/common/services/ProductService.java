package es.iesclaradelrey.da2d1e.shopngprmnmp.common.services;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Categoria;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.models.NewProductModel;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    Product save(Product product);
    void deleteById(Long id);
    boolean existsByName(String name);

    Product createNew(NewProductModel model);
    Product update(Long id, NewProductModel model);

}




