package com.example.stage.Controllers;

import com.example.stage.Utils.CodeUtils;
import com.example.stage.payload.request.ResetPasswordRequest;
import com.example.stage.payload.response.MessageResponse;
import com.example.stage.payload.response.ResetPasswordResponse;
import com.example.stage.services.EmailService;
import com.example.stage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/resetPassword")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@RequestBody ResetPasswordRequest request) {
        String email = request.getEmail();

        if (userService.userExistsByEmail(email)) {
            // Generate reset code
            String code = CodeUtils.generateCode();

            userService.saveResetCode(email, code);
            // Send reset code to user via email
            try {
                emailService.sendResetCodeByEmail(email, code);
                return ResponseEntity.ok(new ResetPasswordResponse("Reset code sent successfully"));
            } catch (MailException e) {

                e.printStackTrace();
                return ResponseEntity.badRequest().body(new ResetPasswordResponse("Failed to send reset code"));
            }
        } else {
           
            return ResponseEntity.badRequest().body(new ResetPasswordResponse("User not found"));
        }
    }



    @PostMapping("/changePassword")
    public ResponseEntity<MessageResponse> changePassword(@RequestBody ResetPasswordRequest request) {
        String email = request.getEmail();
        String newPassword = request.getNewPassword();

        if (userService.userExistsByEmail(email)) {
            userService.changePassword(email, newPassword);
            return ResponseEntity.ok(new MessageResponse("Password changed successfully"));
        } else {

            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
        }
    }

}