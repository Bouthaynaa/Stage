package com.example.stage.Controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.stage.Security.jwt.JwtUtils;
import com.example.stage.services.EmailService;
import com.example.stage.Security.service.UserDetailsImpl;
import com.example.stage.services.VerificationTokenService;
import com.example.stage.entity.ERole;
import com.example.stage.entity.Role;
import com.example.stage.entity.User;
import com.example.stage.payload.request.LoginRequest;
import com.example.stage.payload.request.SignupRequest;
import com.example.stage.payload.response.JwtResponse;
import com.example.stage.payload.response.MessageResponse;
import com.example.stage.repository.RoleRepository;


import com.example.stage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    EmailService emailService;

    @Autowired
    private VerificationTokenService tokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userRepository.getUserByEmail(loginRequest.getEmail());
        System.out.println(user.getId());
        System.out.println(loginRequest.getEmail());
        System.out.println(user.getPassword());
        // Check if the user is verified
        if (!user.isVerified()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Please verify your account before signing in."));
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), loginRequest.getPassword()));
        System.out.println("----------------------"+user.getId()+"---"+user.getEmail()+"---"+user.getUsername()+"---"+user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "soustraitant":
                        Role modRole = roleRepository.findByName(ERole.ROLE_SOUSTRAITANT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;

                    default:

                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        // Generate verification token
        String verificationToken = tokenService.generateVerificationToken();

        // Set verification token and mark user as unverified
        user.setVerificationToken(verificationToken);
        user.setVerified(false);

        // Save user to the database
        userRepository.save(user);

        // Send verification email
        emailService.sendVerificationEmail(user.getEmail(), verificationToken);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PutMapping("/verify")
    public String verifyUser(@RequestParam("token") String token) {
        if (token == null || token.isEmpty()) {
            return "Invalid verification token";
        }

        // Retrieve user by verification token
        User user = userRepository.findByVerificationToken(token);
        if (user == null) {
            return "User not found or already verified";
        }

        // Mark user as verified
        user.setVerified(true);
        user.setVerificationToken(null); // Optionally, clear the verification token after verification
        userRepository.save(user);

        return "User verified successfully";
    }
}
