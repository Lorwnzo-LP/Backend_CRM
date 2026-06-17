package com.VastaImoveis.CRM.Alert.Service;

import com.VastaImoveis.CRM.Alert.Repository.AlertRepository;
import com.VastaImoveis.CRM.Alert.entity.domain.Alert;
import com.VastaImoveis.CRM.Alert.entity.dto.AlertRequestDTO;
import com.VastaImoveis.CRM.Alert.entity.dto.AlertResponseDTO;
import com.VastaImoveis.CRM.Alert.mapper.AlertMapper;
import com.VastaImoveis.CRM.Exception.ResourceNotFoundException;
import com.VastaImoveis.CRM.Lead.Entity.Domain.Lead;
import com.VastaImoveis.CRM.Lead.Repository.LeadRepository;
import com.VastaImoveis.CRM.Users.Entity.Domain.User;
import com.VastaImoveis.CRM.Users.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AlertService {
    private final AlertRepository repository;
    private final LeadRepository leadRepository;
    private final UserRepository userRepository;

    public AlertService(AlertRepository repository, LeadRepository leadRepository, UserRepository userRepository) {
        this.repository = repository;
        this.leadRepository = leadRepository;
        this.userRepository = userRepository;
    }

    public AlertResponseDTO create(AlertRequestDTO dto){
        Lead lead = leadRepository.findById(dto.getLeadId())
                .orElseThrow(() -> new ResourceNotFoundException("Lead não encontrado"));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User não encontrado"));

        Alert alert = AlertMapper.fromDTO(dto);
        alert.setLead(lead);
        alert.setUser(user);

        return AlertMapper.toDTO(repository.save(alert));
    }

    public Page<AlertResponseDTO> findByUserId(UUID userId, @PageableDefault(size = 15) Pageable pageable){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Lead não encontrado"));
        return repository.findByUser(user, pageable).map(AlertMapper::toDTO);
    }

    public AlertResponseDTO patchRead(UUID AlertId, Boolean read){
        Alert alert = repository.findById(AlertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado"));
        alert.setRead(read);
        return AlertMapper.toDTO(repository.save(alert));
    }

    public void delete(UUID AlertId){
        Alert alert = repository.findById(AlertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado"));
        repository.delete(alert);
    }
}
