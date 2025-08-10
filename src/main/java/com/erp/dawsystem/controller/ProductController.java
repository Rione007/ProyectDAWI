package com.erp.dawsystem.controller;

import com.erp.dawsystem.entity.Category;
import com.erp.dawsystem.entity.Product;
import com.erp.dawsystem.service.interfaces.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(page, 5); // 5 productos por página
        Page<Product> productos;

        if ((nombre == null || nombre.isEmpty()) && (categoria == null || categoria.isEmpty())) {
            productos = productService.findAll(pageable);
        } else if (nombre != null && !nombre.isEmpty() && (categoria == null || categoria.isEmpty())) {
            productos = productService.searchByName(nombre, pageable);
        } else if ((nombre == null || nombre.isEmpty()) && categoria != null && !categoria.isEmpty()) {
            productos = productService.findByCategory(Category.valueOf(categoria), pageable);
        } else {
            productos = productService.findByNameContainingAndCategory(nombre, Category.valueOf(categoria), pageable);
        }

        model.addAttribute("products", productos.getContent());
        model.addAttribute("categories", Category.values());
        model.addAttribute("nombre", nombre);
        model.addAttribute("categoria", categoria);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productos.getTotalPages());

        return "productos/catalogo";
    }


    @GetMapping("/stock-control")
    public String stockControl(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Pageable pageable = PageRequest.of(page, 8); // 8 productos por página
        Page<Product> productos;

        if ((nombre == null || nombre.isEmpty()) && (categoria == null || categoria.isEmpty())) {
            productos = productService.findByStockLessThanEqual(14 , pageable);
        } else if (nombre != null && !nombre.isEmpty() && (categoria == null || categoria.isEmpty())) {
            productos = productService.searchByName(nombre, pageable);
        } else if ((nombre == null || nombre.isEmpty()) && categoria != null && !categoria.isEmpty()) {
            productos = productService.findByCategory(Category.valueOf(categoria), pageable);
        } else {
            productos = productService.findByNameContainingAndCategory(nombre, Category.valueOf(categoria), pageable);
        }

        model.addAttribute("products", productos.getContent());
        model.addAttribute("categories", Category.values());
        model.addAttribute("nombre", nombre);
        model.addAttribute("categoria", categoria);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productos.getTotalPages());

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

    @GetMapping("/buscar")
    @ResponseBody
    public List<Product> buscarProductos(@RequestParam String nombre,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "50") int size) {
        Page<Product> result = productService.searchByName(nombre, PageRequest.of(page, size));
        return result.getContent();
    }

}
