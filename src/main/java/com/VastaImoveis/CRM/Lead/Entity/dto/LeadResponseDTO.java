package com.VastaImoveis.CRM.Lead.Entity.dto;

import com.VastaImoveis.CRM.Lead.Entity.Domain.StatusLead;

import java.time.LocalDateTime;
import java.util.UUID;

public class LeadResponseDTO {

    private final UUID id;
    private final UUID userId;
    private final String nome;
    private final String telefone;
    private final String email;
    private final StatusLead status;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UUID getUserId() {
        return userId;
    }

    public LeadResponseDTO(UUID id, UUID userId, String nome, String telefone, String email,
                           StatusLead status,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public StatusLead getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
