package com.pm.authservice.dto;

public class LoginResponseDTO {

    // Once initialize cannot be re-initialize again
    private final String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
