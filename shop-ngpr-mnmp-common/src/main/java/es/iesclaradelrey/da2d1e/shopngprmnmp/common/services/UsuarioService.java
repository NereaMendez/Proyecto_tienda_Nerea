package es.iesclaradelrey.da2d1e.shopngprmnmp.common.services;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Usuario;

import java.util.Optional;

public interface UsuarioService {

    Usuario save(Usuario user);

    Optional<Usuario> findByEmailIgnoreCase(String email);
    Optional<Usuario> findByFullNameIgnoreCase(String fullName);
    Optional<Usuario> findById(Long id);

}
