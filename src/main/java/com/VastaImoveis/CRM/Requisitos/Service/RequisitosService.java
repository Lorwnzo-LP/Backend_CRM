package com.VastaImoveis.CRM.Requisitos.Service;

import com.VastaImoveis.CRM.Requisitos.Entity.Domain.Requisito;
import com.VastaImoveis.CRM.Requisitos.Entity.dto.RequisitoRequestDTO;
import com.VastaImoveis.CRM.Requisitos.Entity.dto.RequisitoResponseDTO;
import com.VastaImoveis.CRM.Requisitos.Repository.RequisitosRepository;
import com.VastaImoveis.CRM.Requisitos.mapper.RequisitoMapper;
import com.VastaImoveis.CRM.Users.Entity.Domain.User;
import com.VastaImoveis.CRM.Users.Repository.UserRepository;
import com.VastaImoveis.CRM.shared.utils.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public class RequisitosService {
    private final RequisitosRepository repository;
    private final UserRepository userRepository;

    public RequisitosService(RequisitosRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public RequisitoResponseDTO create(RequisitoRequestDTO dto) {
        User user = SecurityUtils.getCurrentUser();

        Requisito requisito = new Requisito();
        requisito.setTitle(dto.getTitle());
        requisito.setMessage(dto.getMessage());
        requisito.setStatus(dto.getStatus());
        requisito.setCorretorId(dto.getCorretor());
        requisito.setGerenteId(dto.getGerente());


        return RequisitoMapper.toDTO(repository.save(requisito));
    }

    public Page<RequisitoResponseDTO> findAll(Pageable pageable){
        User user = SecurityUtils.getCurrentUser();

        if(user.getRole().name().equals("GERENTE")){
            return repository.findByGerente(user, pageable)
                    .map(RequisitoMapper::toDTO);
        }

        return repository.findByCorretor(user,pageable)
                .map(RequisitoMapper::toDTO);
    }

    public Page<RequisitoResponseDTO> findAllByUser(UUID userId, Pageable pageable){
        
    }

}
