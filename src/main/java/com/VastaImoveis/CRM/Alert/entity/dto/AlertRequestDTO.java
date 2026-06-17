package com.VastaImoveis.CRM.Alert.entity.dto;

import com.VastaImoveis.CRM.Alert.entity.domain.AlertType;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public class AlertRequestDTO {
    @NotBlank(message = "Título obrigatório")
        private String title;

    @NotBlank(message = "Mensagem obrigatória")
        private String message;

        private UUID leadId;

        private UUID userId;

        private AlertType type;

        private LocalDateTime alarmAt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UUID getLeadId() {
        return leadId;
    }

    public void setLeadId(UUID leadId) {
        this.leadId = leadId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public AlertType getType() {
        return type;
    }

    public void setType(AlertType type) {
        this.type = type;
    }

    public LocalDateTime getAlarmAt() {
        return alarmAt;
    }

    public void setAlarmAt(LocalDateTime alarmAt) {
        this.alarmAt = alarmAt;
    }
}
