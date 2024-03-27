package com.example.stage.payload.response;

import lombok.Data;

@Data
public class ResetPasswordResponse {
    private String message;

    // Constructor, getters, and setters
    public ResetPasswordResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
