package com.VastaImoveis.CRM.Users.Entity.dto;

import com.VastaImoveis.CRM.Users.Entity.Domain.RegiaoUsers;
import com.VastaImoveis.CRM.Users.Entity.Domain.RoleUsers;

import java.util.UUID;

public class UserResponseDTO {
    private UUID id;
    private String nome;
    private  String email;
    private RoleUsers role;
    private RegiaoUsers regiao;

    public UserResponseDTO(UUID id, String nome, String email, RoleUsers role, RegiaoUsers regiao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.role = role;
        this.regiao = regiao;
    }
}
