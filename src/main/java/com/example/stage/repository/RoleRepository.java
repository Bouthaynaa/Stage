package com.example.stage.repository;

import java.util.Optional;

import com.example.stage.entity.ERole;
import com.example.stage.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
