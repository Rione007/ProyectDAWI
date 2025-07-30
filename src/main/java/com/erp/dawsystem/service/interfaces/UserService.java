package com.erp.dawsystem.service.interfaces;

import com.erp.dawsystem.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // Listar todos los usuarios
    List<User> findAll();

    // Obtener usuario por ID
    Optional<User> findById(Long id);

    // Crear usuario (password hasheada)
    User create(User user);

    // Actualizar usuario por ID
    User update(Long id, User user);

    // Eliminar usuario por ID
    void delete(Long id);

    // Buscar usuario por username
    Optional<User> findByUsername(String username);

    // Verificar si un username ya existe
    boolean existsByUsername(String username);

    void cambiarPassword(String username, String nuevaPassword);

}
