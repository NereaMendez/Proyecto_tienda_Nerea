package es.iesclaradelrey.da2d1e.shopngprmnmp.web.controllers;


import es.iesclaradelrey.da2d1e.shopngprmnmp.common.dto.NewUserDto;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Usuario;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.services.UsuarioService;
import es.iesclaradelrey.da2d1e.shopngprmnmp.security.AppUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


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

    @GetMapping("/register")
    public ModelAndView registerForm(HttpServletRequest request) {
        // Usamos un DTO vacío para el formulario inicial
        return getRegisterModelAndView(new NewUserDto(), null, request);
    }

    private ModelAndView getRegisterModelAndView(NewUserDto newUserDto, String errorMessage, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("users/register");

        // IMPORTANTE: Este nombre debe ser el mismo que en el th:object de tu HTML
        mv.addObject("usuario", newUserDto);

        mv.addObject("title", "GEX - Registro");
        mv.addObject("subtitulo", "Crea tu cuenta :P");
        mv.addObject("titulo", "Registro");
        mv.addObject("error", errorMessage);

        /* Tenemos el csrf activado por defecto y thymeleaf lo inyecta automaticamente -- REVISAR */
        return mv;
    }

    @PostMapping("/register")
    public String registerPost(@ModelAttribute("usuario") Usuario usuario,
                               @RequestParam(value = "confirmPassword", required = false) String confirm,
                               @RequestParam(value = "acceptTerms", required = false) Boolean acceptTerms,
                               Model model) {

        List<String> errors = new ArrayList<>();

        // 1. Validar nombre
        if (usuario.getFullName() == null || usuario.getFullName().trim().isEmpty()) {
            errors.add("El nombre completo es obligatorio.");
        } else if (usuario.getFullName().trim().length() < 3) {
            errors.add("El nombre completo debe tener al menos 3 caracteres.");
        }

        // 2. Validar Email (Usando java.util.regex.Pattern)
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            errors.add("El correo electrónico es obligatorio.");
        } else {
            String email = usuario.getEmail().trim();
            String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
            // Ahora sí funcionará porque usaremos java.util.regex.Pattern
            if (!Pattern.matches(emailRegex, email)) {
                errors.add("El formato del correo electrónico no es válido.");
            } else if (usuarioService.findByEmailIgnoreCase(email).isPresent()) {
                errors.add("El correo electrónico ya está registrado.");
            }
        }

        // 3. Validar Contraseña (Mínimo 6)
        String password = usuario.getPassword();
        if (password == null || password.trim().isEmpty()) {
            errors.add("La contraseña es obligatoria.");
        } else if (password.trim().length() < 6) {
            errors.add("La contraseña debe tener al menos 6 caracteres.");
        }

        // 4. Validar que la contraseña coincida
        if (confirm == null || !password.equals(confirm.trim())) {
            errors.add("Las contraseñas no coinciden.");
        }

        // 5. Validar términos (Lo que querías recuperar)
        if (acceptTerms == null || !acceptTerms) {
            errors.add("Debes aceptar los términos y condiciones.");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("usuario", usuario);
            return "users/register";
        }

        try {
            usuario.setPassword(passwordEncoder.encode(password.trim()));
            usuarioService.save(usuario);
            return "redirect:/users/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", "Error inesperado: " + e.getMessage());
            return "users/register";
        }
    }
    /* Cambios realizados */
}