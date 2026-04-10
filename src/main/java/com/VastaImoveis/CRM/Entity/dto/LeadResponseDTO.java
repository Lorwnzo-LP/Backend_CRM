package com.VastaImoveis.CRM.Entity.dto;

import com.VastaImoveis.CRM.Entity.Domain.StatusLead;

import java.time.LocalDateTime;
import java.util.UUID;

public class LeadResponseDTO {

    private UUID id;
    private String nome;
    private String telefone;
    private String email;
    private StatusLead status;
    private String observacoes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public LeadResponseDTO(UUID id, String nome, String telefone, String email,
                           StatusLead status, String observacoes,
                           LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.status = status;
        this.observacoes = observacoes;
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

    public String getObservacoes() {
        return observacoes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
