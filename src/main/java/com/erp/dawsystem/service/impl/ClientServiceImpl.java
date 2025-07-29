package com.erp.dawsystem.service.impl;

import com.erp.dawsystem.entity.Client;
import com.erp.dawsystem.repository.ClientRepository;
import com.erp.dawsystem.service.interfaces.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client create(Client client) {
        // Validar duplicado por email
        if (clientRepository.existsByEmail(client.getEmail())) {
            throw new RuntimeException("Ya existe un cliente con el email: " + client.getEmail());
        }
        return clientRepository.save(client);
    }

    @Override
    public Client update(Long id, Client client) {
        return clientRepository.findById(id)
                .map(existingClient -> {
                    existingClient.setName(client.getName());
                    existingClient.setEmail(client.getEmail());
                    existingClient.setPhone(client.getPhone());
                    existingClient.setAddress(client.getAddress());
                    return clientRepository.save(existingClient);
                })
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    @Override
    public void delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> searchByName(String name) {
        return clientRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }
}
