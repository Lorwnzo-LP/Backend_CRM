package com.VastaImoveis.CRM.Lead.mapper;

import com.VastaImoveis.CRM.Lead.Entity.Domain.Lead;
import com.VastaImoveis.CRM.Lead.Entity.dto.LeadRequestDTO;
import com.VastaImoveis.CRM.Lead.Entity.dto.LeadResponseDTO;

public class LeadMapper {

    public static Lead toEntity(LeadRequestDTO dto) {
        Lead lead = new Lead();
        lead.setNome(dto.getNome());
        lead.setTelefone(dto.getTelefone());
        lead.setEmail(dto.getEmail());
        lead.setStatus(dto.getStatus());
        lead.setObservacoes(dto.getObservacoes());
        return lead;
    }

    public static LeadResponseDTO toDTO(Lead lead) {
        return new LeadResponseDTO(
                lead.getId(),
                lead.getNome(),
                lead.getTelefone(),
                lead.getEmail(),
                lead.getStatus(),
                lead.getObservacoes(),
                lead.getCreatedAt(),
                lead.getUpdatedAt()
        );
    }

    public static void updateEntity(Lead lead, LeadRequestDTO dto) {
        lead.setNome(dto.getNome());
        lead.setTelefone(dto.getTelefone());
        lead.setEmail(dto.getEmail());
        lead.setStatus(dto.getStatus());
        lead.setObservacoes(dto.getObservacoes());
    }
}
