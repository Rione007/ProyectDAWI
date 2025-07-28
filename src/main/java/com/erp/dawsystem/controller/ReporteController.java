package com.erp.dawsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

    // Reporte de ventas por rango de fechas
    @GetMapping("/ventas-fecha")
    public String ventasPorFecha() {
        return "reportes/ventas-fecha";
    }

    // Reporte general de ventas
    @GetMapping("/general")
    public String reporteGeneral() {
        return "reportes/general";
    }
}
