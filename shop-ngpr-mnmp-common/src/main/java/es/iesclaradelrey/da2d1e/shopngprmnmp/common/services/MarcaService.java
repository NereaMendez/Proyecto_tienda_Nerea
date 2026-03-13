package es.iesclaradelrey.da2d1e.shopngprmnmp.common.services;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Marca;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.models.NewMarcaModel;

import java.util.List;
import java.util.Optional;

public interface MarcaService {
    List<Marca> findAll();
    Optional<Marca> findById(Long id);
    Optional<Marca> findByName(String name);
    Marca save(Marca marca);
    void deleteById(Long id);
    boolean existsByName(String name);

    Marca createNew(NewMarcaModel model);
    Marca update(Long id, NewMarcaModel model);
}
