package com.VastaImoveis.CRM.Lead.Service;

import com.VastaImoveis.CRM.Lead.Entity.Domain.Lead;
import com.VastaImoveis.CRM.Lead.Entity.dto.LeadRequestDTO;
import com.VastaImoveis.CRM.Lead.Entity.dto.LeadResponseDTO;
import com.VastaImoveis.CRM.Lead.Repository.LeadRepository;
import com.VastaImoveis.CRM.Exception.BusinessException;
import com.VastaImoveis.CRM.Lead.mapper.LeadMapper;
import com.VastaImoveis.CRM.Lead.utils.SecurityUtils;
import com.VastaImoveis.CRM.Users.Entity.Domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LeadService {

    private final LeadRepository repository;

    public LeadService(LeadRepository repository) {
        this.repository = repository;
    }

    // 🔥 Criar Lead
    public LeadResponseDTO create(LeadRequestDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }
        try {
            User user = SecurityUtils.getCurrentUser();
            Lead lead = LeadMapper.toEntity(dto);
            lead.setUser(user);
            Lead saved = repository.save(lead);
            return LeadMapper.toDTO(saved);
        } catch (Exception e) {
            throw new BusinessException("Erro ao salvar o Lead");
        }


    }

    // 📄 Listar com paginação por usuário
    public Page<LeadResponseDTO> findAll(Pageable pageable) {
        try {
            User user = SecurityUtils.getCurrentUser();
            return repository.findByUser(user, pageable)
                    .map(LeadMapper::toDTO);
        } catch (Exception e) {
            throw new BusinessException("Error ao buscar os Leads");
        }
    }

    // 🔍 Buscar por ID
    public LeadResponseDTO findById(UUID id) {
        Lead lead = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Lead não encontrado"));
        try {
            return LeadMapper.toDTO(lead);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }


    // ✏️ Atualizar
    public LeadResponseDTO update(UUID id, LeadRequestDTO dto) {
        Lead lead = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Lead não encontrado"));
        // 🔥 Regra: evitar duplicidade de email
        if (!lead.getEmail().equals(dto.getEmail()) &&
                repository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }

        try {
            LeadMapper.updateEntity(lead, dto);
            Lead updated = repository.save(lead);

            return LeadMapper.toDTO(updated);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ❌ Deletar
    public void delete(UUID id) {
        Lead lead = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Lead não encontrado"));
        try {
            repository.delete(lead);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
