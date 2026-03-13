package es.iesclaradelrey.da2d1e.shopngprmnmp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("")
    public ModelAndView admin() {
        ModelAndView mv = new ModelAndView("admin/indexAdmin");
        return mv;
    }

    //Revisar
    @GetMapping("/")
    public String adminConBarra() {
        return "redirect:/admin";
    }

}
