package es.iesclaradelrey.da2d1e.shopngprmnmp.common.repositories;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByName(String name);
    boolean existsByName(String name);
}