package es.iesclaradelrey.da2d1e.shopngprmnmp.common.services;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Usuario;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario save(Usuario user) {
        return usuarioRepository.save(user);
    }

    @Override
    public Optional<Usuario> findByEmailIgnoreCase(String email) {
        return usuarioRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public Optional<Usuario> findByFullNameIgnoreCase(String fullName) {
        return usuarioRepository.findByFullNameIgnoreCase(fullName);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }
}