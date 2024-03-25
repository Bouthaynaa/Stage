package com.example.stage.services;

import com.example.stage.repository.SousTraitantRepository;
import org.springframework.stereotype.Service;

@Service
public class SousTraitantService {
    private final SousTraitantRepository sousTraitantRepository;

    public SousTraitantService(SousTraitantRepository sousTraitantRepository) {
        this.sousTraitantRepository = sousTraitantRepository;
    }


}
