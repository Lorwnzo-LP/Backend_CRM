package com.VastaImoveis.CRM.Lead.Service;

import com.VastaImoveis.CRM.Exception.BusinessException;
import com.VastaImoveis.CRM.Lead.Entity.Domain.Lead;
import com.VastaImoveis.CRM.Lead.Entity.dto.*;
import com.VastaImoveis.CRM.Lead.Repository.LeadRepository;
import com.VastaImoveis.CRM.Lead.mapper.LeadMapper;
import com.VastaImoveis.CRM.Users.Entity.Domain.User;
import com.VastaImoveis.CRM.shared.utils.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class LeadService {

    private final LeadRepository repository;

    public LeadService(LeadRepository repository) {
        this.repository = repository;
    }

    // 🔥 Criar Lead
    public LeadResponseDTO create(LeadRequestDTO dto) {
        if (repository.existsByEmail(dto.getEmail()) && !dto.getEmail().isEmpty()) {
            throw new BusinessException("Email já cadastrado");
        }

        User user = SecurityUtils.getCurrentUser();
        Lead lead = LeadMapper.toEntity(dto);
        lead.setUser(user);
        Lead saved = repository.save(lead);
        return LeadMapper.toDTO(saved);
    }

    // 📄 Listar com paginação por usuário
    public Page<LeadResponseDTO> findAllWithPage(Pageable pageable) {

        User user = SecurityUtils.getCurrentUser();

        if (user.getRole().name().equals("GERENTE")) {
            return repository.findAll(pageable)
                    .map(LeadMapper::toDTO);
        }

        return repository.findByUser(user, pageable)
                .map(LeadMapper::toDTO);

    }

    public List<LeadResponseDTO> findAllByUserIdList(UUID id) {
        return repository.findByUserId(id).stream().map(LeadMapper::toDTO).toList();
    }

    // Listar filtrado por usuário (gerente only)
    public Page<LeadResponseDTO> findAllByUser(UUID userId, Pageable pageable) {
        User user = SecurityUtils.getCurrentUser();
        if (!user.getRole().name().equals("Gerente")) {
            throw new BusinessException("Você não tem permissão para essa chamada");
        }
        return repository.findByUserId(userId, pageable).map(LeadMapper::toDTO);
    }

    // 🔍 Buscar por ID
    public LeadResponseDTO findById(UUID id) {
        User user = SecurityUtils.getCurrentUser();
        Lead lead = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Lead não encontrado"));
        if (user.getRole().name().equals("CORRETOR") &&
                !lead.getUser().getId().equals(user.getId())) {
            throw new BusinessException("Você não pode acessar este lead");
        }

        return LeadMapper.toDTO(lead);

    }


    // ✏️ Atualizar
    public LeadResponseDTO update(UUID id, LeadRequestDTO dto) {
        User user = SecurityUtils.getCurrentUser();

        Lead lead = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Lead não encontrado"));

        if (user.getRole().name().equals("CORRETOR") &&
                !lead.getUser().getId().equals(user.getId())) {
            throw new BusinessException("Você não pode editar este Lead");
        }

        // 🔥 Regra: evitar duplicidade de email
        if (!lead.getEmail().equals(dto.getEmail()) &&
                repository.existsByEmail(dto.getEmail())
        ) {
            throw new BusinessException("Email já cadastrado");
        }

        Lead updated = repository.save(LeadMapper.updateEntity(lead, dto));

        return LeadMapper.toDTO(updated);

    }

    public LeadResponseDTO updateStatus(
            UUID id,
            UpdateLeadStatusDTO dto
    ) {
        Lead lead = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Lead não encontrado"));

        lead.setStatus(dto.statusLead());
        Lead updatedLead = repository.save(lead);

        return LeadMapper.toDTO(updatedLead);
    }

    // ❌ Deletar
    public void delete(UUID id) {
        User user = SecurityUtils.getCurrentUser();

        if (!user.getRole().name().equals("GERENTE")) {
            throw new BusinessException("Apenas gerentes podem deletar leads");
        }

        Lead lead = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Lead não encontrado"));


        repository.delete(lead);
    }


    public LeadDashboardDTO getDashboard() {

        User user = SecurityUtils.getCurrentUser();

        boolean isGerente = user.getRole().name().equals("GERENTE");

        // 👇 null = todos os leads
        List<StatusCount> result = repository.countByStatus(isGerente ? null : user);

        Map<String, Long> porStatus = new HashMap<>();
        long total = 0;

        for (StatusCount row : result) {
            porStatus.put(row.getStatus().name(), row.getCount());
            total += row.getCount();
        }

        return new LeadDashboardDTO(total, porStatus);
    }

    public LeadDashboardDTO getDashboardByUser(UUID userId) {

        User user = SecurityUtils.getCurrentUser();

        boolean isGerente = user.getRole().name().equals("GERENTE");

        // 👇 null = todos os leads
        List<StatusCount> result = repository.countByStatusByUser(userId);

        Map<String, Long> porStatus = new HashMap<>();
        long total = 0;

        for (StatusCount row : result) {
            porStatus.put(row.getStatus().name(), row.getCount());
            total += row.getCount();
        }

        return new LeadDashboardDTO(total, porStatus);
    }
}
