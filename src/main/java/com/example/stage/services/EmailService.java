package com.example.stage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendVerificationEmail(String to, String verificationToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Account Verification");
        mailMessage.setText("Please click the following link to verify your account: "
                + "http://localhost:4200/activate?token=" + verificationToken);
        javaMailSender.send(mailMessage);
    }


    public void sendResetCodeByEmail(String recipientEmail, String resetCode) throws MailException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject("Password Reset Code");
        mailMessage.setText("Your password reset code is: " + resetCode);
        javaMailSender.send(mailMessage);
    }
}
