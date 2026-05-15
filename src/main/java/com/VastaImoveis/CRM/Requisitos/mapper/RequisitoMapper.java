package com.VastaImoveis.CRM.Requisitos.mapper;

import com.VastaImoveis.CRM.Requisitos.Entity.Domain.Requisito;
import com.VastaImoveis.CRM.Requisitos.Entity.dto.RequisitoRequestDTO;
import com.VastaImoveis.CRM.Requisitos.Entity.dto.RequisitoResponseDTO;

public class RequisitoMapper {

    public static RequisitoResponseDTO toDTO(Requisito requisito){

        return new RequisitoResponseDTO(
                requisito.getId(),
                requisito.getCorretorId(),
                requisito.getGerenteId(),
                requisito.getTitle(),
                requisito.getMessage(),
                requisito.getStatus(),
                requisito.getCreatedAt()
        );
    }

    public static Requisito toEntity(RequisitoRequestDTO request){
        Requisito requisito = new Requisito();
        requisito.setCorretorId(request.getCorretor());
        requisito.setGerenteId(request.getGerente());
        requisito.setTitle(request.getTitle());
        requisito.setMessage(request.getMessage());
        requisito.setStatus(request.getStatus());

        return requisito;
    }
}
