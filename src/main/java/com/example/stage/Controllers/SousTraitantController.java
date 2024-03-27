package com.example.stage.Controllers;

import com.example.stage.entity.SousTraitant;
import com.example.stage.repository.SousTraitantRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sous-traitants")
public class SousTraitantController {
    private final SousTraitantRepository sousTraitantRepository;

    public SousTraitantController(SousTraitantRepository sousTraitantRepository) {
        this.sousTraitantRepository = sousTraitantRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<SousTraitant> getAllSousTraitants() {
        return sousTraitantRepository.findAll();
    }

    @GetMapping("/{id}")
    public SousTraitant getSousTraitantById(@PathVariable Long id) {
        return sousTraitantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sous-traitant Id:" + id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public SousTraitant createSousTraitant(@RequestBody SousTraitant sousTraitant) {
        return sousTraitantRepository.save(sousTraitant);
    }

    @PutMapping("/{id}")
    public SousTraitant updateSousTraitant(@PathVariable Long id, @RequestBody SousTraitant sousTraitantDetails) {
        SousTraitant sousTraitant = sousTraitantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid sous-traitant Id:" + id));

        sousTraitant.setTitre(sousTraitantDetails.getTitre());
        sousTraitant.setEmail(sousTraitantDetails.getEmail());
        sousTraitant.setPhoneNumber(sousTraitantDetails.getPhoneNumber());
        sousTraitant.setFirstName(sousTraitantDetails.getFirstName());
        sousTraitant.setLastName(sousTraitantDetails.getLastName());


        return sousTraitantRepository.save(sousTraitant);
    }

    @DeleteMapping("/{id}")
    public void deleteSousTraitant(@PathVariable Long id) {
        sousTraitantRepository.deleteById(id);
    }
}
