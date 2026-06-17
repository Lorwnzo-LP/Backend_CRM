package com.VastaImoveis.CRM.Alert.entity.domain;

import com.VastaImoveis.CRM.Lead.Entity.Domain.Lead;
import com.VastaImoveis.CRM.Users.Entity.Domain.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Alert {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    private String message;

    private Boolean read = false;

    private LocalDateTime createdAt;

    private LocalDateTime alarmAt;

    @ManyToOne
    private Lead lead;

    @ManyToOne
    private User user;

    private AlertType type;

    public Alert(String title, String message, AlertType type, LocalDateTime alarmAt) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.alarmAt = alarmAt;
    }

    @PrePersist
    public void PrePersist(){
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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