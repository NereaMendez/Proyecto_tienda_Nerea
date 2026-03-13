package es.iesclaradelrey.da2d1e.shopngprmnmp.web.controllers;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Categoria;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Marca;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.entities.Product;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.mappers.ProductMapper;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.models.NewProductModel;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.services.CategoriaService;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.services.MarcaService;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoriaService categoriaService;
    private final MarcaService marcaService;

    public ProductController(ProductService productService, CategoriaService categoriaService, MarcaService marcaService) {
        this.productService = productService;
        this.categoriaService = categoriaService;
        this.marcaService = marcaService;
    }


    @GetMapping("/products")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("products");
        var products = productService.findAll();
        mv.addObject("products", products);
        mv.addObject("categorias", categoriaService.findAll());
        //Cambio 27/feb
        mv.addObject("marcas", marcaService.findAll());

        return mv;
    }

    /*como hemos ignorado los espacios? de la siguiente forma:
    * name=${#strings.replace(#strings.toLowerCase(product.name), ' ', '-')
    * reemplazamos los espacios por guiones
    * SEO Friendly - dirección web clara y descriptiva que ayuda a los buscadores y a los usuarios
    * a entender fácilmente el contenido de la página*/
    @GetMapping("/products/{id}/{name}")
    public ModelAndView detail (@PathVariable Long id, @PathVariable String name) {
        ModelAndView mv = new ModelAndView("detailProduct");
        var product = productService.findById(id).orElse(null);
        mv.addObject("product", product);
        mv.addObject("categorias", categoriaService.findAll());
        //Cambio 27/feb
        mv.addObject("marcas", marcaService.findAll());

        return mv;
    }

    /*MÉTODOS DE ADMINISTRACIÓN*/
    @GetMapping("/admin/products")
    public ModelAndView adminList() {
        ModelAndView mv = new ModelAndView("admin/products/productsList");
        mv.addObject("products", productService.findAll());
        return mv;
    }

    //REDIRECCIÓN SI VIENE CON BARRA
    @GetMapping("/admin/products/")
    public String adminListaConBarra() {
        return "redirect:/admin/products";
    }

    /*MÉTODOS DE ADMINISTRACIÓN - FORMS */
    @GetMapping("/admin/products/create")
    public String create(Model model) {
        model.addAttribute("product", new NewProductModel());
        model.addAttribute("isEdit", false);
        return "admin/products/productForm";
    }

    @PostMapping("/admin/products/save")
    public String save(@ModelAttribute("product") NewProductModel productModel, Model model) {
        try {
            productService.createNew(productModel);
            return "redirect:/admin/products";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al crear: " + e.getMessage());
            model.addAttribute("isEdit", false);
            return "admin/products/productForm";
        }
    }

    @GetMapping("/admin/products/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));

        NewProductModel productModel = ProductMapper.map(product);

        model.addAttribute("product", productModel);
        model.addAttribute("id", id);
        model.addAttribute("isEdit", true);
        return "admin/products/productForm";
    }

    @PostMapping("/admin/products/edit/{id}")
    public String saveEdit(@PathVariable Long id, @ModelAttribute("product") NewProductModel productModel, Model model) {
        try {
            productService.update(id, productModel);
            return "redirect:/admin/products";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al editar: " + e.getMessage());
            model.addAttribute("id", id);
            model.addAttribute("isEdit", true);
            return "admin/products/productForm";
        }
    }

    @GetMapping("/admin/products/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("admin/products/productDelete");
        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con ID: " + id));
        mv.addObject("product", product);
        return mv;
    }
    //procesar el borrado
    @PostMapping("/admin/products/delete/{id}")
    public String procesarDelete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Producto eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al eliminar: " + e.getMessage());
        }
        return "redirect:/admin/products";
    }

    @ModelAttribute("marcas")
    public List<Marca> getAllMarcas() {
        return marcaService.findAll();
    }

    @ModelAttribute("todasLasCategorias")
    public List<Categoria> getAllCategorias() {
        return categoriaService.findAll();
    }


}
