package com.VastaImoveis.CRM.LeadNotes.mapper;

import com.VastaImoveis.CRM.Lead.Entity.Domain.Lead;
import com.VastaImoveis.CRM.LeadNotes.Entity.domain.LeadNote;
import com.VastaImoveis.CRM.LeadNotes.Entity.dto.LeadNoteRequestDTO;
import com.VastaImoveis.CRM.LeadNotes.Entity.dto.LeadNoteResponseDTO;

public class LeadNoteMapper {
    public static LeadNote toEntity(LeadNoteRequestDTO dto){
        LeadNote leadNote = new LeadNote();
        leadNote.setNote(dto.getNote());
        return leadNote;
    }

    public static LeadNoteResponseDTO toDTO(LeadNote leadNote){
        return new LeadNoteResponseDTO(
                leadNote.getId(),
                leadNote.getLead().getId(),
                leadNote.getNote(),
                leadNote.getCreatedAt()
        );
    }
}
