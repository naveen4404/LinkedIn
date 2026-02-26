package com.linkedin.linkedin.features.authentication.controller;

import com.linkedin.linkedin.features.authentication.dto.AuthenticationUserRequest;
import com.linkedin.linkedin.features.authentication.dto.AuthenticationUserResponse;
import com.linkedin.linkedin.features.authentication.model.AuthenticationUser;
import com.linkedin.linkedin.features.authentication.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @GetMapping("/user")
    public  AuthenticationUser getUser(@RequestAttribute("authenticatedUser") AuthenticationUser user){
        return user;
    }

    @PostMapping("/register")
    public AuthenticationUserResponse register(@Valid @RequestBody AuthenticationUserRequest newUser) throws MessagingException, UnsupportedEncodingException {
        return authenticationService.register(newUser);
    }

    @PostMapping("/login")
    public AuthenticationUserResponse login(@Valid @RequestBody AuthenticationUserRequest loginUser){
        return authenticationService.login(loginUser);
    }

    @GetMapping("/send-email-verification-token")
    public String sendEmailVerificationToken(@RequestAttribute("authenticatedUser") AuthenticationUser user){
        authenticationService.sendEmailVerificationToken(user.getEmail());
        return "Email verification token sent successfully";
    }

    @PutMapping("/validate-email-verification-token")
    public String verifyEmail(@RequestParam String token, @RequestAttribute("authenticatedUser") AuthenticationUser user){
        authenticationService.validateEmailVerificationToken(token, user.getEmail());
        return "Email verified successfully";
    }

    @PutMapping("/send-password-reset-token")
    public String sendPasswordResetToken(@RequestParam String email){
        authenticationService.sendPasswordResetToken(email);
        return "password reset token sent successfully";
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestParam String newPassword, @RequestParam String token, @RequestParam String email){
        authenticationService.resetPassword(email,newPassword,token);
        return "Password reset successful";
    }

}
