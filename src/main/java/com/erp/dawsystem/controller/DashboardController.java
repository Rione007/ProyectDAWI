package com.erp.dawsystem.controller;

import com.erp.dawsystem.service.interfaces.ClientService;
import com.erp.dawsystem.service.interfaces.ProductService;
import com.erp.dawsystem.service.interfaces.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public String dashboard(Model model) {
        double ventasHoy = saleService.getTotalVentasHoy();       // Total ventas del día
        double ventasMes = saleService.getTotalVentasMes();       // Total ventas del mes actual
        int stockBajo = productService.countProductosStockBajo(); // Productos con stock bajo
        int totalClientes = clientService.countClientes();        // Clientes registrados

        // Enviamos los datos al HTML index.html
        model.addAttribute("ventasHoy", ventasHoy);
        model.addAttribute("ventasMes", ventasMes);
        model.addAttribute("stockBajo", stockBajo);
        model.addAttribute("totalClientes", totalClientes);

        return "index";
    }
    

}
