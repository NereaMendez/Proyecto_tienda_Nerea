package es.iesclaradelrey.da2d1e.shopngprmnmp.web.controllers;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Categoria;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Marca;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.mappers.MarcaMapper;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.models.NewMarcaModel;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.services.CategoriaService;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.services.MarcaService;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MarcaController {

    private final ProductService productService;
    private final CategoriaService categoriaService;
    private final MarcaService marcaService;

    public MarcaController(ProductService productService, CategoriaService categoriaService, MarcaService marcaService) {
        this.productService = productService;
        this.categoriaService = categoriaService;
        this.marcaService = marcaService;
    }

    @GetMapping("/marcas")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("marcas");
        // Cargamos los productos generales por si la vista los necesita
        mv.addObject("products", productService.findAll());
        return mv;
    }

    @GetMapping("/marcas/{id}")
    public ModelAndView detail(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("detailMarca");
        Marca marca = marcaService.findById(id).orElse(null);
        mv.addObject("marca", marca);
        return mv;
    }

    /* MÉTODOS DE ADMINISTRACIÓN - Lista */
    @GetMapping("/admin/marcas")
    public ModelAndView adminList() {
        ModelAndView mv = new ModelAndView("admin/marcas/marcasList");
        mv.addObject("marcas", marcaService.findAll());
        return mv;
    }

    @GetMapping("/admin/marcas/")
    public String adminListaConBarra() {
        return "redirect:/admin/marcas";
    }

    /* MÉTODOS DE ADMINISTRACIÓN - Form */

    @GetMapping("/admin/marcas/create")
    public String create(Model model) {
        model.addAttribute("marca", new NewMarcaModel());
        model.addAttribute("isEdit", false);
        return "admin/marcas/marcaForm";
    }

    @PostMapping("/admin/marcas/save")
    public String save(@ModelAttribute("marca") NewMarcaModel marcaModel, Model model) {
        try {
            marcaService.createNew(marcaModel);
            return "redirect:/admin/marcas";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al crear la marca: " + e.getMessage());
            model.addAttribute("isEdit", false);
            return "admin/marcas/marcaForm";
        }
    }

    @GetMapping("/admin/marcas/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Marca marca = marcaService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada con ID: " + id));

        // Uso del Mapper para pasar de Entidad a Modelo
        NewMarcaModel marcaModel = MarcaMapper.map(marca);

        model.addAttribute("marca", marcaModel);
        model.addAttribute("id", id);
        model.addAttribute("isEdit", true);
        return "admin/marcas/marcaForm";
    }

    @PostMapping("/admin/marcas/edit/{id}")
    public String saveEdit(@PathVariable Long id, @ModelAttribute("marca") NewMarcaModel marcaModel, Model model) {
        try {
            marcaService.update(id, marcaModel);
            return "redirect:/admin/marcas";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al editar la marca: " + e.getMessage());
            model.addAttribute("id", id);
            model.addAttribute("isEdit", true);
            return "admin/marcas/marcaForm";
        }
    }

    @GetMapping("/admin/marcas/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("admin/marcas/marcaDelete");
        Marca marca = marcaService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada con ID: " + id));
        mv.addObject("marca", marca);
        return mv;
    }

    @PostMapping("/admin/marcas/delete/{id}")
    public String procesarDelete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Marca marca = marcaService.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada con ID: " + id));

            // Lógica de seguridad: no borrar si tiene productos

            if (marca.getProducts() != null && !marca.getProducts().isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        "No se puede eliminar la marca porque tiene productos asociados");
                return "redirect:/admin/marcas";
            }

            marcaService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Marca eliminada correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/admin/marcas";
    }

    // --- ATRIBUTOS GLOBALES PARA LAS VISTAS ---

    @ModelAttribute("marcas")
    public List<Marca> getAllMarcas() {
        return marcaService.findAll();
    }

    @ModelAttribute("categorias")
    public List<Categoria> getAllCategorias() {
        return categoriaService.findAll();
    }
}