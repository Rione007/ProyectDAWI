package com.erp.dawsystem.controller;

import com.erp.dawsystem.entity.Client;
import com.erp.dawsystem.service.interfaces.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // 👉 Mostrar lista con filtro
    @GetMapping("/gestion_cliente")
    public String listarClientes(@RequestParam(required = false) String filtro, Model model) {
        List<Client> clientes = (filtro == null || filtro.isEmpty())
                ? clientService.findAll()
                : clientService.searchByName(filtro);

        model.addAttribute("clientes", clientes);
        model.addAttribute("filtro", filtro);
        return "mantenimiento/cliente/gestion_cliente";
    }

    // 👉 Mostrar formulario para nuevo cliente
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("cliente", new Client());
        return "mantenimiento/cliente/NuevoCliente";
    }

    // 👉 Mostrar formulario para editar cliente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Client> optCliente = clientService.findById(id);
        if (optCliente.isPresent()) {
            model.addAttribute("cliente", optCliente.get());
            return "mantenimiento/cliente/EditarClient";
        } else {
            return "redirect:/clientes/gestion_cliente";
        }
    }

    // 👉 Guardar cliente (crear o actualizar)
    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute("cliente") Client cliente, Model model) {
        boolean esNuevo = (cliente.getId() == null);

        if (esNuevo && clientService.existsByEmail(cliente.getEmail())) {
            model.addAttribute("error", "Ya existe un cliente con ese correo.");
            return "mantenimiento/cliente/NuevoCliente";
        }

        if (!esNuevo) {
            // Validar si está cambiando a un email que ya existe
            Optional<Client> existente = clientService.findById(cliente.getId());
            if (existente.isPresent() && !existente.get().getEmail().equals(cliente.getEmail())
                    && clientService.existsByEmail(cliente.getEmail())) {
                model.addAttribute("error", "Ya existe un cliente con ese correo.");
                return "mantenimiento/cliente/EditarClient";
            }
        }

        if (esNuevo) {
            clientService.create(cliente);
            model.addAttribute("mensaje", "Cliente registrado exitosamente.");
        } else {
            clientService.update(cliente.getId(), cliente);
            model.addAttribute("mensaje", "Cliente actualizado exitosamente.");
        }

        return "redirect:/clientes/gestion_cliente";
    }

    // 👉 Eliminar cliente
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id, Model model) {
        clientService.delete(id);
        return "redirect:/clientes/gestion_cliente?mensaje=Cliente eliminado correctamente";
    }

    // 👉 Buscar clientes desde JS (opcional)
    @GetMapping("/buscar")
    @ResponseBody
    public List<Client> buscarClientes(@RequestParam String nombre) {
        return clientService.searchByName(nombre);
    }

    // 👉 Obtener un cliente por ID -- nuevo metodo
    @GetMapping("/{id}")
    @ResponseBody
    public Client obtenerClientePorId(@PathVariable Long id) {
        return clientService.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

}
