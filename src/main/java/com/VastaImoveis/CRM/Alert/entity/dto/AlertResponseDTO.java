package com.VastaImoveis.CRM.Alert.entity.dto;

import com.VastaImoveis.CRM.Alert.entity.domain.AlertType;
import com.VastaImoveis.CRM.Lead.Entity.Domain.Lead;
import com.VastaImoveis.CRM.Users.Entity.Domain.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class AlertResponseDTO {

    private final UUID id;
    private final Lead lead;
    private final User user;
    private final LocalDateTime createdAt;
    private final LocalDateTime alarmAt;
    private final String title;
    private final String message;
    private final AlertType type;
    private final Boolean read;

    public AlertResponseDTO(UUID id, Lead lead, User user, LocalDateTime createdAt, LocalDateTime alarmAt, String title, String message, AlertType type, Boolean read) {
        this.id = id;
        this.lead = lead;
        this.user = user;
        this.createdAt = createdAt;
        this.alarmAt = alarmAt;
        this.title = title;
        this.message = message;
        this.type = type;
        this.read = read;
    }

    public UUID getId() {
        return id;
    }

    public Lead getLead() {
        return lead;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getAlarmAt() {
        return alarmAt;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public AlertType getType() {
        return type;
    }

    public Boolean getRead() {
        return read;
    }
}
