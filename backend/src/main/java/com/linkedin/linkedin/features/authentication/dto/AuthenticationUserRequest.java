package com.linkedin.linkedin.features.authentication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AuthenticationUserRequest {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Enter a valid email")
    private String email;
    @NotBlank(message = "Enter a valid password")
    private String password;

    public AuthenticationUserRequest() {
    }

    public AuthenticationUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
