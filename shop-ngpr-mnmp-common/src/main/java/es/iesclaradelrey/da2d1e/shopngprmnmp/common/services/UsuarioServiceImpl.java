package es.iesclaradelrey.da2d1e.shopngprmnmp.common.services;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.dto.NewUserDto;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.AppRol;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Usuario;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.mappers.UsuarioMapper;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;


    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    //Cambio metodo save: Nerea, para que guarde el usuario...
    @Override
    public Usuario save(Usuario usuario) {
        // Si el usuario es nuevo (no tiene ID todavía)
        if (usuario.getUserId() == null) {

            //fuerza que sea un registro nuevo
            usuario.setUserId(null);

            //Ponemos la fecha de registro
            usuario.setDateTimeRegistration(java.time.LocalDateTime.now());

            //Creamos el objeto Rol 'USER' manualmente
            AppRol appRolUser = new AppRol();
            appRolUser.setId("USER");

            //añadimos a la lista de roles del usuario
            usuario.getRoles().add(appRolUser);
        }

        // Guardamos en la base de datos
        return usuarioRepository.save(usuario);
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

    @Override
    public Usuario register(NewUserDto newUserDto) {
        Usuario nuevoUsuario = UsuarioMapper.map(newUserDto);
        return usuarioRepository.save(nuevoUsuario);
    }

}