package com.VastaImoveis.CRM.Lead.Service;

import com.VastaImoveis.CRM.Exception.BusinessException;
import com.VastaImoveis.CRM.Lead.Entity.Domain.Lead;
import com.VastaImoveis.CRM.Lead.Entity.Domain.StatusLead;
import com.VastaImoveis.CRM.Lead.Entity.dto.LeadDashboardDTO;
import com.VastaImoveis.CRM.Lead.Entity.dto.LeadRequestDTO;
import com.VastaImoveis.CRM.Lead.Entity.dto.LeadResponseDTO;
import com.VastaImoveis.CRM.Lead.Entity.dto.StatusCount;
import com.VastaImoveis.CRM.Lead.Repository.LeadRepository;
import com.VastaImoveis.CRM.Lead.mapper.LeadMapper;
import com.VastaImoveis.CRM.LeadNotes.repository.LeadNoteRepository;
import com.VastaImoveis.CRM.shared.utils.SecurityUtils;
import com.VastaImoveis.CRM.Users.Entity.Domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LeadService {

    private final LeadRepository repository;
    private final LeadNoteRepository leadNoteRepository;
    public LeadService(LeadRepository repository, LeadNoteRepository leadNoteRepository) {
        this.repository = repository;
        this.leadNoteRepository = leadNoteRepository;
    }

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

    public Page<LeadResponseDTO> findAllWithPage(Pageable pageable) {

        User user = SecurityUtils.getCurrentUser();

        if (user.getRole().name().equals("GERENTE")) {
            Page<Lead> page = repository.findAll(pageable);
            List<UUID> leadIds = page.getContent()
                    .stream().map(Lead::getId).toList();
            Set<UUID> leadsWithNotes = new HashSet<>(leadNoteRepository.findLeadIdsWithNotes(leadIds));
            return page.map(lead -> {
                LeadResponseDTO dto = LeadMapper.toDTO(lead);
                dto.setHasNotes(leadsWithNotes.contains(lead.getId()));
                return dto;
            });
        }
        Page<Lead> page = repository.findByUser(user, pageable);
        List<UUID> leadIds = page.getContent()
                .stream().map(Lead::getId).toList();
        Set<UUID> leadsWithNotes = new HashSet<>(leadNoteRepository.findLeadIdsWithNotes(leadIds));
        return page.map(lead -> {
            LeadResponseDTO dto = LeadMapper.toDTO(lead);
            dto.setHasNotes(leadsWithNotes.contains(lead.getId()));
            return dto;
        });
    };

    public Page<LeadResponseDTO> findByStatus(Pageable pageable, StatusLead status) {
        User user = SecurityUtils.getCurrentUser();

        if(!user.getRole().name().equals("GERENTE") && status.equals(StatusLead.ENCERRADO)){
            throw new BusinessException("Você não tem acesso a essa chamada");
        }

        Page<Lead> page = repository.findByStatus(status, pageable);
        List<UUID> leadIds = page.getContent()
                .stream().map(Lead::getId).toList();
        Set<UUID> leadsWithNotes = new HashSet<>(leadNoteRepository.findLeadIdsWithNotes(leadIds));
        return page.map(lead -> {
            LeadResponseDTO dto = LeadMapper.toDTO(lead);
            dto.setHasNotes(leadsWithNotes.contains(lead.getId()));
            return dto;
        });
    }

    public Page<LeadResponseDTO> findAllNotEncerrado(Pageable pageable){
        Page<Lead> page = repository.findByStatusNot(StatusLead.ENCERRADO, pageable);
        List<UUID> leadIds = page.getContent()
                .stream().map(Lead::getId).toList();
        Set<UUID> leadsWithNotes = new HashSet<>(leadNoteRepository.findLeadIdsWithNotes(leadIds));
        return page.map(lead -> {
            LeadResponseDTO dto = LeadMapper.toDTO(lead);
            dto.setHasNotes(leadsWithNotes.contains(lead.getId()));
            return dto;
        });
    }

    public Page<LeadResponseDTO> findBySearch(Pageable pageable, String search){
        return repository.search(search, pageable).map(LeadMapper::toDTO);
    }

    public List<LeadResponseDTO> findAllByUserIdList(UUID id){
        return repository.findByUserId(id).stream().map(LeadMapper::toDTO).toList();
    }

    public Page<LeadResponseDTO> findAllByUser(UUID userId, Pageable pageable){
        User user = SecurityUtils.getCurrentUser();
        if(!user.getRole().name().equals("Gerente")){
            throw new BusinessException("Você não tem permissão para essa chamada");
        }
        return repository.findByUserId(userId, pageable).map(LeadMapper::toDTO);
    }

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
                repository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }

        Lead updated = repository.save(LeadMapper.updateEntity(lead, dto));

        return LeadMapper.toDTO(updated);
    }

    public LeadResponseDTO patchLeadStatus(UUID id, StatusLead status){
        Lead lead = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Lead não encontrado"));
        lead.setStatus(status);
        Lead patched = repository.save(lead);
        return LeadMapper.toDTO(patched);
    }

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
