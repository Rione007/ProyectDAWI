package com.erp.dawsystem.repository;

import com.erp.dawsystem.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Buscar por nombre ignorando mayúsculas
    List<Client> findByNameContainingIgnoreCase(String name);

    // Buscar cliente por email
    Optional<Client> findByEmail(String email);

    // Verificar si ya existe un email (para evitar duplicados)
    boolean existsByEmail(String email);

    // Buscar por dirección ignorando mayúsculas
    List<Client> findByAddressContainingIgnoreCase(String address);


    // ✅ Soporte de búsqueda paginada
    Page<Client> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
