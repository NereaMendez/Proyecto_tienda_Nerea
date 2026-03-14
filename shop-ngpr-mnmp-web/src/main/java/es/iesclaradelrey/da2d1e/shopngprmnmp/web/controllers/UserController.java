package es.iesclaradelrey.da2d1e.shopngprmnmp.web.controllers;


import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Usuario;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.services.UsuarioService;
import es.iesclaradelrey.da2d1e.shopngprmnmp.security.AppUserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UsuarioService usuarioService;

    public UserController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal AppUserDetails userDetails, Model model) {
        Usuario usuario = usuarioService.findByEmailIgnoreCase(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("No se ha encontrado al usuario!"));

        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Mi Perfil");
        return "users/profile";
    }

    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.userId")
    @GetMapping("/profile/{userId}")
    public String profileById(@PathVariable Long userId, Model model) {
        Usuario usuario = usuarioService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        model.addAttribute("usuario", usuario);
        model.addAttribute("titulo", "Perfil de " + usuario.getFullName());
        return "users/profile";
    }


    /* AQUI FALTA CREAR EL MAPPING PARA /login y /login/ y /register tal, se supone que deberias recibir los datos
    con lo q tenemos ya en esta clase*/

}

