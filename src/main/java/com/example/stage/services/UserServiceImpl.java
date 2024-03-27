package com.example.stage.services;

import com.example.stage.entity.User;
import com.example.stage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean userExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void saveResetCode(String email, String code) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetCode(code);
            userRepository.save(user);
        }
    }
    @Autowired
    private PasswordEncoder encoder;
    public void changePassword(String email, String newPassword) {
        // Retrieve user by email
        User user = userRepository.findByEmail(email);
        if (user != null) {
            // Encode the new password
            String encodedPassword = encoder.encode(newPassword);
            // Update user's password
            user.setPassword(encodedPassword);
            // Save updated user entity
            userRepository.save(user);
        } else {
            // Handle user not found
            throw new RuntimeException("User with email " + email + " not found");
        }
    }

}