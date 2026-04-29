package com.VastaImoveis.CRM.Users.mapper;

import com.VastaImoveis.CRM.Users.Entity.Domain.User;
import com.VastaImoveis.CRM.Users.Entity.dto.UserResponseDTO;

public class userMapper {
    public static UserResponseDTO toDTO(User user){
        return new UserResponseDTO(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                user.getRole(),
                user.getRegiao()
        );
    }

}
