package com.example.stage.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerificationTokenService {
    public String generateVerificationToken() {
        return UUID.randomUUID().toString();
    }
}