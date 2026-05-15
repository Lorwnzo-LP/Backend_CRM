package com.VastaImoveis.CRM.Requisitos.mapper;

import com.VastaImoveis.CRM.Requisitos.Entity.Domain.Requisito;
import com.VastaImoveis.CRM.Requisitos.Entity.Domain.StatusRequisito;
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
        return updateEntity(requisito, request);
    }

    public static Requisito updateEntity(Requisito request, RequisitoRequestDTO dto){
        request.setTitle(dto.getTitle());
        request.setMessage(dto.getMessage());
        request.setStatus(dto.getStatus());
        return request;
    }

    public static Requisito updateEntityStatus(Requisito request, StatusRequisito status){
        request.setStatus(status);
        return request;
    }
}
