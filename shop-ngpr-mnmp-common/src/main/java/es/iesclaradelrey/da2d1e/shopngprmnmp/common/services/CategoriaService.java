package es.iesclaradelrey.da2d1e.shopngprmnmp.common.services;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Categoria;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.models.NewCategoriaModel;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<Categoria> findAll();
    List<Categoria> findAllById(List<Long> ids);
    Optional<Categoria> findById(Long id);
    Optional<Categoria> findByName(String name);
    Categoria save(Categoria categoria);
    void deleteById(Long id);
    boolean existsByName(String name);

    Categoria createNew(NewCategoriaModel newCategoriaModel);
    Categoria update(Long id, NewCategoriaModel newCategoriaModel);
}