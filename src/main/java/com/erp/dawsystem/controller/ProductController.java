package com.erp.dawsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductController {

    @GetMapping("/catalogo")
    public String mostrarCatalogo(Model model) {

        return "productos/catalogo";
    }

    @GetMapping("/stock")
    public String controlStock(Model model) {
        return "productos/stock";
    }
}
