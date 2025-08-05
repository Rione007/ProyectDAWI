package com.erp.dawsystem.controller;

import com.erp.dawsystem.entity.Client;
import com.erp.dawsystem.service.interfaces.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    // Buscar clientes por nombre (ignora mayúsculas/minúsculas)
    @GetMapping("/buscar")
    @ResponseBody
    public List<Client> buscarClientes(@RequestParam String nombre) {
        return clientService.searchByName(nombre);
    }
}
