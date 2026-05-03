package com.VastaImoveis.CRM.Auth.dto;

public class AuthResponseDTO {
    private String Token;

    public AuthResponseDTO(String token) {
        this.Token = token;
    }

    public String getToken() {
        return Token;
    }
}
