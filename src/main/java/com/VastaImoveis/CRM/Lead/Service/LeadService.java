package com.VastaImoveis.CRM.Lead.Service;

import com.VastaImoveis.CRM.Lead.Entity.Domain.Lead;
import com.VastaImoveis.CRM.Lead.Entity.dto.LeadRequestDTO;
import com.VastaImoveis.CRM.Lead.Entity.dto.LeadResponseDTO;
import com.VastaImoveis.CRM.Lead.Repository.LeadRepository;
import com.VastaImoveis.CRM.Lead.exception.BusinessException;
import com.VastaImoveis.CRM.Lead.exception.ResourceNotFoundException;
import com.VastaImoveis.CRM.Lead.mapper.LeadMapper;
import org.hibernate.tool.schema.spi.CommandAcceptanceException;
import org.hibernate.tool.schema.spi.ExceptionHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
            throw new RuntimeException("Email já cadastrado");
        }

        Lead lead = LeadMapper.toEntity(dto);
        Lead saved = repository.save(lead);

        return LeadMapper.toDTO(saved);

        }

    // 📄 Listar com paginação
    public Page<LeadResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(LeadMapper::toDTO);
    }

    // 🔍 Buscar por ID
    public LeadResponseDTO findById(UUID id) {
        Lead lead = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead não encontrado"));

        return LeadMapper.toDTO(lead);
    }

    // ✏️ Atualizar
    public LeadResponseDTO update(UUID id, LeadRequestDTO dto) {
        Lead lead = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead não encontrado"));

        // 🔥 Regra: evitar duplicidade de email
        if (!lead.getEmail().equals(dto.getEmail()) &&
                repository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }

        LeadMapper.updateEntity(lead, dto);
        Lead updated = repository.save(lead);

        return LeadMapper.toDTO(updated);
    }

    // ❌ Deletar
    public void delete(UUID id) {
        Lead lead = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead não encontrado"));

        repository.delete(lead);
    }
}
