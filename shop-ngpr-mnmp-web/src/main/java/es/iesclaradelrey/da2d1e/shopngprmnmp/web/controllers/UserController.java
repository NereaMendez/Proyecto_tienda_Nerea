package es.iesclaradelrey.da2d1e.shopngprmnmp.web.controllers;


import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Usuario;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.services.UsuarioService;
import es.iesclaradelrey.da2d1e.shopngprmnmp.security.AppUserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
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

    //login

    @GetMapping("/login")
    public String login(Authentication authentication,
                        @RequestParam(value = "error", required = false) String error,
                        Model model) {


        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }

        //Si hay ?error en la URL, mensaje
        if (error != null) {
            model.addAttribute("error", "Usuario y/o contraseña incorrectos");
        }


        return "users/login";
    }

    //register

    @GetMapping("/register")
    public String register(Model model, Authentication authentication) {


        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("usuario", new Usuario());
        return "users/register";
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("usuario") Usuario usuario,
                               @RequestParam(value = "confirmPassword", required = false) String confirm,
                               @RequestParam(value = "acceptTerms", required = false) Boolean acceptTerms,
                               Model model) {

        List<String> errors = new ArrayList<>();

        //VALIDACIÓN MANUAL

        if (usuarioService.findByEmailIgnoreCase(usuario.getEmail()).isPresent()) {
            errors.add("El correo electrónico ya está registrado.");
        }


        if (acceptTerms == null || !acceptTerms) {
            errors.add("Debes aceptar los términos y condiciones.");

        }

        //Que la contrasena coincida con confirm
        if (usuario.getPassword() == null || !usuario.getPassword().equals(confirm)) {
            errors.add("Las contraseñas no coinciden.");
        }

        if (usuario.getPassword().isBlank()) {
            errors.add("La contraseña es obligatoria.");
        }


        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "users/register";
        }

        try {
            //Encriptamos antes de guardar (Seguridad básica)
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioService.save(usuario);
            return "redirect:/users/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", "Error inesperado: " + e.getMessage());
            return "users/register";
        }
    }
}
