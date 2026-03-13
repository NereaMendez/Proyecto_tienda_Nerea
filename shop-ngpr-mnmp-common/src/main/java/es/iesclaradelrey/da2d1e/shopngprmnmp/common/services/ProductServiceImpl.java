package es.iesclaradelrey.da2d1e.shopngprmnmp.common.services;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Marca;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.mappers.ProductMapper;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.models.NewProductModel;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MarcaService marcaService;

    @Autowired // Necesario para las categorías
    private CategoriaService categoriaService;

    private final Comparator<Product> alphabeticalComparator =
            (p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName());

    @Override
    public List<Product> findAll() {
        return productRepository.findAll()
                .stream()
                .sorted(alphabeticalComparator)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public Product createNew(NewProductModel newProductModel) {
        // 1. Mapeamos datos básicos
        Product product = ProductMapper.map(newProductModel);

        // 2. Buscamos y asignamos la marca (usando el servicio, estilo profesor)
        Marca marca = marcaService.findById(newProductModel.getMarcaId())
                .orElseThrow(() -> new EntityNotFoundException("No se encuentra marca con ID " + newProductModel.getMarcaId()));
        product.setMarca(marca);

        // 3. Asignamos categorías si las hay
        if (newProductModel.getCategoryIds() != null && !newProductModel.getCategoryIds().isEmpty()) {
            product.setCategories(categoriaService.findAllById(newProductModel.getCategoryIds()));
        }

        // 4. Imagen por defecto
        if (product.getImage() == null || product.getImage().isBlank()) {
            product.setImage("/images/categories/No_Image_Available.jpg");
        }

        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, NewProductModel model) {
        // 1. Verificamos que existe
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID " + id));

        // 2. Actualizamos campos
        product.setProductCode(model.getProductCode());
        product.setName(model.getName());
        product.setDescription(model.getDescription());
        product.setPrice(model.getPrice());
        product.setDiscount(model.getDiscount());
        product.setImage(model.getImage());

        // 3. Actualizamos marca
        Marca marca = marcaService.findById(model.getMarcaId())
                .orElseThrow(() -> new EntityNotFoundException("Marca no encontrada"));
        product.setMarca(marca);

        // 4. Actualizamos categorías
        product.setCategories(categoriaService.findAllById(model.getCategoryIds()));

        return productRepository.save(product);
    }
}