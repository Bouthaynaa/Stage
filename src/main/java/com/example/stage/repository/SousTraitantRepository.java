package com.example.stage.repository;


import com.example.stage.entity.SousTraitant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SousTraitantRepository extends JpaRepository<SousTraitant, Long> {
    // You can add custom query methods here if needed
}
