package com.tech.restaurant_reserve_pr.repository;

import com.tech.restaurant_reserve_pr.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNombre(String nombre);
}
