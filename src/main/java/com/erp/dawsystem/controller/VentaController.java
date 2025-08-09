package com.erp.dawsystem.controller;

import com.erp.dawsystem.entity.Client;
import com.erp.dawsystem.entity.Product;
import com.erp.dawsystem.entity.Sale;
import com.erp.dawsystem.entity.SaleDetail;
import com.erp.dawsystem.service.interfaces.ClientService;
import com.erp.dawsystem.service.interfaces.ProductService;
import com.erp.dawsystem.service.interfaces.SaleDetailService;
import com.erp.dawsystem.service.interfaces.SaleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final SaleService saleService;
    private final ClientService clientService;
    private final ProductService productService;
    private final SaleDetailService saleDetailService;

    public VentaController(SaleService saleService,
                           ClientService clientService,
                           ProductService productService,
                           SaleDetailService saleDetailService) {
        this.saleService = saleService;
        this.clientService = clientService;
        this.productService = productService;
        this.saleDetailService = saleDetailService;
    }

    // Vista para crear una nueva venta
    @GetMapping("/nueva")
    public String nuevaVenta() {
        return "ventas/nuevaVenta";
    }

    // Guardar venta desde formulario (AJAX)
    @PostMapping("/guardar")
    @ResponseBody
    public String guardarVenta(
            @RequestParam Long clientId,
            @RequestParam String date,
            @RequestParam List<Long> productIds,
            @RequestParam List<Integer> quantities,
            @RequestParam List<BigDecimal> prices) {

        Client cliente = clientService.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Sale venta = new Sale();
        venta.setClient(cliente);
        venta.setDate(LocalDate.parse(date));

        List<SaleDetail> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (int i = 0; i < productIds.size(); i++) {
            Long productId = productIds.get(i);
            int cantidad = quantities.get(i);
            BigDecimal precio = prices.get(i);

            Product producto = productService.findById(productId);

            SaleDetail detalle = new SaleDetail();
            detalle.setSale(venta);
            detalle.setProduct(producto);
            detalle.setQuantity(cantidad);
            detalle.setUnitPrice(precio);
            detalle.setSubtotal(precio.multiply(BigDecimal.valueOf(cantidad)));

            detalles.add(detalle);
            total = total.add(detalle.getSubtotal());
        }

        venta.setTotal(total);
        venta.setDetails(detalles);
        saleService.save(venta);

        return "Venta guardada correctamente";
    }

    // ✅ Listado de ventas con filtros y paginación
    @GetMapping("/listado")
    public String listadoVentas(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @RequestParam(required = false) String cliente,
            @PageableDefault(size = 7, sort = "date", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {

        Page<Sale> ventas;

        // 🗓 Filtro por fechas
        if (fechaInicio != null && fechaFin != null) {
            ventas = saleService.findByDateBetween(fechaInicio, fechaFin, pageable);
        }
        // 🔍 Filtro por nombre de cliente
        else if (cliente != null && !cliente.isEmpty()) {
            ventas = saleService.searchByClientName(cliente, pageable);
        }
        // 🔁 Sin filtros (todos los datos paginados)
        else {
            ventas = saleService.findAll(pageable);
        }

        model.addAttribute("ventas", ventas);
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);
        model.addAttribute("cliente", cliente);
        model.addAttribute("page", ventas); // Para usar en el HTML con paginación

        return "ventas/listado";
    }

    // Ver detalles de una venta específica
    @GetMapping("/detalle/{id}")
    public String detalleVenta(@PathVariable Long id, Model model) {
        Sale venta = saleService.findById(id);
        if (venta == null) {
            return "redirect:/ventas/listado";
        }

        List<SaleDetail> detalles = saleDetailService.findBySaleId(id);

        model.addAttribute("venta", venta);
        model.addAttribute("detalles", detalles);

        return "ventas/detalle";
    }
}
