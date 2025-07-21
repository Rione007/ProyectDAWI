package com.erp.dawsystem.repository;

import com.erp.dawsystem.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByName(String name);

    List<Client> findByNameContainingIgnoreCase(String name);

    Client findByEmail(String email);

    List<Client> findByAddressContainingIgnoreCase(String address);
}

