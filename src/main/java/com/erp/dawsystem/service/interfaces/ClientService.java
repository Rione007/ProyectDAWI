package com.erp.dawsystem.service.interfaces;

import com.erp.dawsystem.entity.Client;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {

    // Listar todos los clientes
    List<Client> findAll();

    // Obtener cliente por ID
    Optional<Client> findById(Long id);

    // Crear cliente (validando duplicado de email)
    Client create(Client client);

    // Actualizar cliente
    Client update(Long id, Client client);

    // Eliminar cliente por ID
    void delete(Long id);

    // Buscar cliente por nombre (ignorando mayúsculas)
    List<Client> searchByName(String name);

    // Verificar duplicado de email
    boolean existsByEmail(String email);

    int countClientes();

    // ✅ Nuevo método para paginación
    Page<Client> findAllPaginated(Pageable pageable);

    // ✅ Búsqueda paginada
    Page<Client> searchByNamePaginated(String name, Pageable pageable);
}
