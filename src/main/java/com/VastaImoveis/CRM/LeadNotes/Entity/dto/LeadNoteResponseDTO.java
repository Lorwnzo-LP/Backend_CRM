package com.VastaImoveis.CRM.LeadNotes.Entity.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class LeadNoteResponseDTO {
    private final UUID id;
    private final String note;
    private final LocalDateTime createdAt;

    public LeadNoteResponseDTO(UUID id, String note, LocalDateTime createdAt) {
        this.id = id;
        this.note = note;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
