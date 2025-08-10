package com.erp.dawsystem.controller;

import com.erp.dawsystem.entity.Category;
import com.erp.dawsystem.entity.Product;
import com.erp.dawsystem.service.interfaces.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/productos")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/catalogo")
    public String catalog(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria,
            Model model) {

        List<Product> productos;

        if ((nombre == null || nombre.isEmpty()) && (categoria == null || categoria.isEmpty())) {
            productos = productService.findAll();
        } else if (nombre != null && !nombre.isEmpty() && (categoria == null || categoria.isEmpty())) {
            productos = productService.searchByName(nombre);
        } else if ((nombre == null || nombre.isEmpty()) && categoria != null && !categoria.isEmpty()) {
            productos = productService.findByCategory(Category.valueOf(categoria));
        } else {
            productos = productService.findByNameContainingAndCategory(nombre, Category.valueOf(categoria));
        }

        model.addAttribute("products", productos);
        model.addAttribute("categories", Category.values());
        model.addAttribute("nombre", nombre);
        model.addAttribute("categoria", categoria);

        return "productos/catalogo";
    }


    @GetMapping("/stock-control")
    public String stockControl(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria,
            Model model) {

        List<Product> productos;

        if ((nombre == null || nombre.isEmpty()) && (categoria == null || categoria.isEmpty())) {
            productos = productService.findByStockLessThanEqual(14);
        } else if (nombre != null && !nombre.isEmpty() && (categoria == null || categoria.isEmpty())) {
            productos = productService.searchByName(nombre);
        } else if ((nombre == null || nombre.isEmpty()) && categoria != null && !categoria.isEmpty()) {
            productos = productService.findByCategory(Category.valueOf(categoria));
        } else {
            productos = productService.findByNameContainingAndCategory(nombre, Category.valueOf(categoria));
        }

        model.addAttribute("products", productos);
        model.addAttribute("categories", Category.values());
        model.addAttribute("nombre", nombre);
        model.addAttribute("categoria", categoria);

        return "productos/stock-control";
    }

    @PostMapping("/adjust-stock")
    public String adjustStock(@RequestParam Long id, @RequestParam int cantidad) {
        Product producto = productService.findById(id);

        if (producto != null) {
            int nuevoStock = producto.getStock() + cantidad;
            producto.setStock(nuevoStock);
            productService.save(producto);
        }

        return "redirect:/productos/stock-control";
    }

    @GetMapping("/detail/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Product producto = productService.findById(id);
        if (producto == null) {
            return "redirect:/productos/catalog";
        }
        model.addAttribute("producto", producto);
        return "productos/detail";
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Product producto = productService.findById(id);
        if (producto == null) {
            return "redirect:/productos/catalogo";
        }
        model.addAttribute("producto", producto);
        model.addAttribute("categories", Category.values());
        return "productos/edit";
    }

    @PostMapping("/edit/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Product product) {
        product.setId(id);
        productService.update(product);
        return "redirect:/productos/catalogo";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/productos/catalogo";
    }

    @GetMapping("/create")
    public String crearProductoForm(Model model) {
        model.addAttribute("producto", new Product());
        model.addAttribute("categories", Category.values());
        return "productos/create";
    }

    @PostMapping("/create")
    public String guardarProducto(@ModelAttribute Product producto) {
        productService.save(producto);
        return "redirect:/productos/catalogo";
    }

    // Buscar productos por nombre
    @GetMapping("/buscar")
    @ResponseBody
    public List<Product> buscarProductos(@RequestParam String nombre) {
        return productService.searchByName(nombre);
    }

}

