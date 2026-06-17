package com.VastaImoveis.CRM.Alert.mapper;

import com.VastaImoveis.CRM.Alert.entity.domain.Alert;
import com.VastaImoveis.CRM.Alert.entity.dto.AlertRequestDTO;
import com.VastaImoveis.CRM.Alert.entity.dto.AlertResponseDTO;

public class AlertMapper {
    public static AlertResponseDTO toDTO(Alert alert){
        return new AlertResponseDTO(
                alert.getId(),
                alert.getLead(),
                alert.getUser(),
                alert.getCreatedAt(),
                alert.getCreatedAt(),
                alert.getTitle(),
                alert.getMessage(),
                alert.getType(),
                alert.getRead()

        );
    }

    public static Alert fromDTO(AlertRequestDTO dto){
        return new Alert(dto.getTitle(), dto.getMessage(), dto.getType(), dto.getAlarmAt());
    }
}
