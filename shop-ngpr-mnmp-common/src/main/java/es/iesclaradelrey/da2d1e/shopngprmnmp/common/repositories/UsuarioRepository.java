package es.iesclaradelrey.da2d1e.shopngprmnmp.common.repositories;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // SOLO LA DECLARACIÓN, SIN IMPLEMENTACIÓN
    Optional<Usuario> findByEmailIgnoreCase(String email);
}