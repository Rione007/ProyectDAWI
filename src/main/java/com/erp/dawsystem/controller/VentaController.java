package com.erp.dawsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @GetMapping("/nueva")
    public String nuevaVenta() {
        return "ventas/nuevaVenta"; // templates/ventas/nueva.html
    }

    @GetMapping("/listado")
    public String listadoVentas() {
        return "ventas/listado"; // templates/ventas/listado.html
    }


}
