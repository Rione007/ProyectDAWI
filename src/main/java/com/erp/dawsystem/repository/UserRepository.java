package com.erp.dawsystem.repository;

import com.erp.dawsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Buscar un usuario por su username
    Optional<User> findByUsername(String username);

    // Verificar si ya existe un username
    boolean existsByUsername(String username);

    List<User> findByUsernameContainingIgnoreCase(String username);
}
