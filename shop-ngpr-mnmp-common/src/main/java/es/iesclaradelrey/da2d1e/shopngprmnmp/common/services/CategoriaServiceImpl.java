package es.iesclaradelrey.da2d1e.shopngprmnmp.common.services;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Categoria;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.mappers.CategoriaMapper;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.models.NewCategoriaModel;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }
    @Override
    public List<Categoria> findAllById(List<Long> ids) {
        return categoriaRepository.findAllById(ids);
    }

    @Override
    public Optional<Categoria> findById(Long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public Optional<Categoria> findByName(String name) {
        return categoriaRepository.findByName(name);
    }

    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void deleteById(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return categoriaRepository.existsByName(name);
    }

    @Override
    public Categoria createNew(NewCategoriaModel newCategoriaModel) {
        Categoria categoria = CategoriaMapper.map(newCategoriaModel);

        if (categoria.getImage() == null || categoria.getImage().isBlank()) {
            categoria.setImage("/images/categories/default.jpg");
        }

        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria update(Long id, NewCategoriaModel newCategoriaModel) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));

        categoria.setName(newCategoriaModel.getName());
        categoria.setDescription(newCategoriaModel.getDescription());
        categoria.setImage(newCategoriaModel.getImage());

        return categoriaRepository.save(categoria);
    }


}