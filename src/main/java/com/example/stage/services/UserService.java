package com.example.stage.services;

public interface UserService {
    boolean userExistsByEmail(String email);
    void saveResetCode(String email, String code);

    void changePassword(String email, String newPassword);
}
