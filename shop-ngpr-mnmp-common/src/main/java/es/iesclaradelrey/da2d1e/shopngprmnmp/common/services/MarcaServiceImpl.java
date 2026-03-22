package es.iesclaradelrey.da2d1e.shopngprmnmp.common.services;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.dto.NewBrandDto;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Marca;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.mappers.MarcaMapper;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.models.NewMarcaModel;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Service
public class MarcaServiceImpl implements MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Override
    public List<Marca> findAll() {
        return marcaRepository.findAll();
    }

    @Override
    public Optional<Marca> findById(Long id) {
        return  marcaRepository.findById(id);
    }

    @Override
    public Optional<Marca> findByName(String name) {
        return  marcaRepository.findByName(name);
    }

    @Override
    public Marca save(Marca product) {
        return  marcaRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        marcaRepository.deleteById(id);
    }
    @Override
    public boolean existsByName(String name) {
        return  marcaRepository.existsByName(name);
    }

    /*
    @Override
    public Marca createNew(NewMarcaModel newMarcaModel) {
        // 1. VALIDACIÓN: Evitar que el nombre sea nulo, vacío o solo espacios
        if (newMarcaModel.getName() == null || newMarcaModel.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre de la marca es obligatorio y no puede estar vacío.");
        }

        // 2. Mapeamos el modelo a la entidad
        Marca marca = MarcaMapper.map(newMarcaModel);

        // 3. Guardamos
        return marcaRepository.save(marca);
    }

    @Override
    public Marca update(Long id, NewMarcaModel model) {
        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe la marca con ID: " + id));
        if (model.getName() == null || model.getName().isBlank()) {
            throw new IllegalArgumentException("El nombre de la marca no puede estar vacío.");
        }
        marca.setName(model.getName());
        return marcaRepository.save(marca);
    }
    */

    @Override
    public Marca createNew(NewBrandDto newBrandDto) {
        // Usamos el mapper que creamos al principio
        Marca marca = MarcaMapper.map(newBrandDto);
        return marcaRepository.save(marca);
    }

    @Override
    public Marca update(Long id, NewBrandDto newBrandDto) {
        Marca marcaExistente = marcaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada"));

        // Actualizamos los datos
        marcaExistente.setName(newBrandDto.getName());

        return marcaRepository.save(marcaExistente);
    }

}
