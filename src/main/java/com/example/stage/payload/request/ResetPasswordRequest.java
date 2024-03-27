package com.example.stage.payload.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private String newPassword; // New field for the new password

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
