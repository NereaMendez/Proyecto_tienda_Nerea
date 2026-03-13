package es.iesclaradelrey.da2d1e.shopngprmnmp.web.controllers;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Categoria;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Marca;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.mappers.CategoriaMapper;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.models.NewCategoriaModel;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.services.CategoriaService;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.services.MarcaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final MarcaService marcaService;

    public CategoriaController(CategoriaService categoriaService, MarcaService marcaService) {
        this.categoriaService = categoriaService;
        this.marcaService = marcaService;
    }

    // Listado de categorías (Público)
    @GetMapping("/categories")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("categories");
        // No hace falta añadir marcas ni categorías, se encargan los @ModelAttribute
        return mv;
    }

    // Detalle de categoría (Público)
    @GetMapping("/categories/{id}")
    public ModelAndView detail(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("detail");
        var categoria = categoriaService.findById(id).orElse(null);
        mv.addObject("categoria", categoria);
        return mv;
    }

    /* MÉTODOS DE ADMINISTRACIÓN - Listas */
    @GetMapping("/admin/categories")
    public ModelAndView adminList() {
        ModelAndView mv = new ModelAndView("admin/categories/categoriesList");
        mv.addObject("categories", categoriaService.findAll());
        return mv;
    }

    // REDIRECCIÓN SI VIENE CON BARRA
    @GetMapping("/admin/categories/")
    public String adminListaConBarra() {
        return "redirect:/admin/categories";
    }

    /* MÉTODOS DE ADMINISTRACIÓN - Forms */
    @GetMapping("/admin/categories/create")
    public String create(Model model) {
        model.addAttribute("categoria", new NewCategoriaModel());
        model.addAttribute("isEdit", false);
        return "admin/categories/categoryForm";
    }

    @PostMapping("/admin/categories/save")
    public String save(@ModelAttribute("categoria") NewCategoriaModel categoriaModel, Model model) {
        try {
            categoriaService.createNew(categoriaModel);
            return "redirect:/admin/categories";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al crear la categoría: " + e.getMessage());
            model.addAttribute("isEdit", false);
            return "admin/categories/categoryForm";
        }
    }

    @GetMapping("/admin/categories/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + id));

        // Mapeo de Entidad a Modelo
        NewCategoriaModel categoriaModel = CategoriaMapper.map(categoria);

        model.addAttribute("categoria", categoriaModel);
        model.addAttribute("id", id);
        model.addAttribute("isEdit", true);
        return "admin/categories/categoryForm";
    }

    @PostMapping("/admin/categories/edit/{id}")
    public String saveEdit(@PathVariable Long id,
                           @ModelAttribute("categoria") NewCategoriaModel categoriaModel,
                           Model model) {
        try {
            categoriaService.update(id, categoriaModel);
            return "redirect:/admin/categories";
        } catch (Exception e) {
            // Recuperar la categoría original para mantener los datos
            Categoria categoriaOriginal = categoriaService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

            model.addAttribute("categoria", categoriaModel); // Los datos que envió el usuario
            model.addAttribute("id", id);
            model.addAttribute("isEdit", true);
            model.addAttribute("errorMessage", "Error al editar la categoría: " + e.getMessage());

            // Asegurar que la imagen no se pierda
            if (categoriaModel.getImage() == null || categoriaModel.getImage().isEmpty()) {
                categoriaModel.setImage(categoriaOriginal.getImage());
            }

            return "admin/categories/categoryForm";
        }
    }

    @GetMapping("/admin/categories/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("admin/categories/categoryDelete");
        Categoria categoria = categoriaService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + id));
        mv.addObject("categoria", categoria);
        return mv;
    }

    @PostMapping("/admin/categories/delete/{id}")
    public String procesarDelete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Categoria categoria = categoriaService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + id));

            // Verificar si tiene productos asociados antes de borrar
            if (categoria.getProducts() != null && !categoria.getProducts().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "No se puede eliminar la categoría porque tiene productos asociados");
                return "redirect:/admin/categories";
            }

            categoriaService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Categoría eliminada correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/admin/categories";
    }

    @ModelAttribute("marcas")
    public List<Marca> getAllMarcas() {
        return marcaService.findAll();
    }

    @ModelAttribute("categorias")
    public List<Categoria> getAllCategorias() {
        return categoriaService.findAll();
    }
}