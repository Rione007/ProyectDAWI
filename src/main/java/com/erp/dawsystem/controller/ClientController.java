package com.erp.dawsystem.controller;

import com.erp.dawsystem.entity.Client;
import com.erp.dawsystem.service.interfaces.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // ✅ Listado con paginación y filtro
    @GetMapping("/gestion_cliente")
    public String listarClientes(
            @RequestParam(required = false) String filtro,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Client> clientesPage;

        if (filtro == null || filtro.isEmpty()) {
            clientesPage = clientService.findAllPaginated(pageable);
        } else {
            clientesPage = clientService.searchByNamePaginated(filtro, pageable);
        }

        model.addAttribute("clientes", clientesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", clientesPage.getTotalPages());
        model.addAttribute("totalItems", clientesPage.getTotalElements());
        model.addAttribute("filtro", filtro);

        return "mantenimiento/cliente/gestion_cliente";
    }

    // ✅ Formulario nuevo cliente
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String filtro,
            Model model) {
        model.addAttribute("cliente", new Client());
        model.addAttribute("page", page);
        model.addAttribute("filtro", filtro);
        return "mantenimiento/cliente/NuevoCliente";
    }

    // ✅ Formulario editar cliente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String filtro,
            Model model) {
        Optional<Client> optCliente = clientService.findById(id);
        if (optCliente.isPresent()) {
            model.addAttribute("cliente", optCliente.get());
            model.addAttribute("page", page);
            model.addAttribute("filtro", filtro);
            return "mantenimiento/cliente/EditarClient";
        } else {
            return "redirect:/clientes/gestion_cliente?page=" + page + "&filtro=" + (filtro != null ? filtro : "");
        }
    }

    // ✅ Guardar cliente (crear o actualizar)
    @PostMapping("/guardar")
    public String guardarCliente(
            @ModelAttribute("cliente") Client cliente,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String filtro,
            Model model) {

        boolean esNuevo = (cliente.getId() == null);

        if (esNuevo && clientService.existsByEmail(cliente.getEmail())) {
            model.addAttribute("error", "Ya existe un cliente con ese correo.");
            return "mantenimiento/cliente/NuevoCliente";
        }

        if (!esNuevo) {
            Optional<Client> existente = clientService.findById(cliente.getId());
            if (existente.isPresent() && !existente.get().getEmail().equals(cliente.getEmail())
                    && clientService.existsByEmail(cliente.getEmail())) {
                model.addAttribute("error", "Ya existe un cliente con ese correo.");
                return "mantenimiento/cliente/EditarClient";
            }
        }

        if (esNuevo) {
            clientService.create(cliente);
        } else {
            clientService.update(cliente.getId(), cliente);
        }

        return "redirect:/clientes/gestion_cliente?page=" + page + "&filtro=" + (filtro != null ? filtro : "");
    }

    // ✅ Eliminar cliente
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String filtro) {

        // Eliminar el cliente
        clientService.delete(id);

        // Comprobar cuántos quedan en la página actual
        Pageable pageable = PageRequest.of(page, 5); // 5 es tu size por defecto
        Page<Client> clientesPage;

        if (filtro == null || filtro.isEmpty()) {
            clientesPage = clientService.findAllPaginated(pageable);
        } else {
            clientesPage = clientService.searchByNamePaginated(filtro, pageable);
        }

        // Si no hay datos en la página actual y no estamos en la primera, retroceder una página
        if (clientesPage.isEmpty() && page > 0) {
            page--;
        }

        return "redirect:/clientes/gestion_cliente?page=" + page +
                "&filtro=" + (filtro != null ? filtro : "");
    }


    // Buscar clientes por nombre (opcional AJAX)
    @GetMapping("/buscar")
    @ResponseBody
    public java.util.List<Client> buscarClientes(@RequestParam String nombre) {
        return clientService.searchByName(nombre);
    }
}
