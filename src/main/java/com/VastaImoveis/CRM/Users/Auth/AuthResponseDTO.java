package com.VastaImoveis.CRM.Users.Auth;

public class AuthResponseDTO {
    private String Token;

    public AuthResponseDTO(String token) {
        this.Token = token;
    }

    public String getToken() {
        return Token;
    }
}
