package com.VastaImoveis.CRM.Users.Entity.dto;

import com.VastaImoveis.CRM.Users.Entity.Domain.RegiaoUsers;
import com.VastaImoveis.CRM.Users.Entity.Domain.RoleUsers;

import java.util.UUID;

public class UserResponseDTO {
    private UUID id;
    private String nome;
    private String email;
    private RoleUsers role;
    private RegiaoUsers regiao;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleUsers getRole() {
        return role;
    }

    public void setRole(RoleUsers role) {
        this.role = role;
    }

    public RegiaoUsers getRegiao() {
        return regiao;
    }

    public void setRegiao(RegiaoUsers regiao) {
        this.regiao = regiao;
    }

    public UserResponseDTO(UUID id, String nome, String email, RoleUsers role, RegiaoUsers regiao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.role = role;
        this.regiao = regiao;
    }
}
