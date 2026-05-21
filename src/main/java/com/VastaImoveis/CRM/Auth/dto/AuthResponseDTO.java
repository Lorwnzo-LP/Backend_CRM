package com.VastaImoveis.CRM.Auth.dto;

import com.VastaImoveis.CRM.Users.Entity.dto.UserResponseDTO;

public class AuthResponseDTO {
    private String token;
    private UserResponseDTO user;

    public AuthResponseDTO(UserResponseDTO user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
