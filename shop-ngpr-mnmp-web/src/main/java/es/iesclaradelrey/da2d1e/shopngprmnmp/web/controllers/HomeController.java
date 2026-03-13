package es.iesclaradelrey.da2d1e.shopngprmnmp.web.controllers;

import es.iesclaradelrey.da2d1e.shopngprmnmp.common.services.CategoriaService;
import es.iesclaradelrey.da2d1e.shopngprmnmp.common.services.MarcaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    private final CategoriaService categoriaService;
    private final MarcaService marcaService;

    public HomeController(CategoriaService categoriaService, MarcaService marcaService) {
        this.categoriaService = categoriaService;
        this.marcaService = marcaService;
    }

    @GetMapping({"", "/"})
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");

        //aniadir todos los objetos categoria al index.
        mv.addObject("categorias", categoriaService.findAll());

        mv.addObject("marcas", marcaService.findAll());


        return mv; //que devuelva la mv de "index.html"
    }

}
