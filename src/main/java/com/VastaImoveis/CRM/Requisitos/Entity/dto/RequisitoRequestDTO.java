package com.VastaImoveis.CRM.Requisitos.Entity.dto;

import com.VastaImoveis.CRM.Requisitos.Entity.Domain.StatusRequisito;
import com.VastaImoveis.CRM.Users.Entity.Domain.User;
import jakarta.validation.constraints.NotBlank;

public class RequisitoRequestDTO {

    @NotBlank(message = "Title é obrigatório")
    private String title;

    @NotBlank(message = "O texto é obrigatório")
    private String message;

    private StatusRequisito status;

    private User corretor;

    private User gerente;

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

    public StatusRequisito getStatus() {
        return status;
    }

    public void setStatus(StatusRequisito status) {
        this.status = status;
    }

    public User getCorretor() {
        return corretor;
    }

    public void setCorretor(User corretor) {
        this.corretor = corretor;
    }

    public User getGerente() {
        return gerente;
    }

    public void setGerente(User gerente) {
        this.gerente = gerente;
    }
}
