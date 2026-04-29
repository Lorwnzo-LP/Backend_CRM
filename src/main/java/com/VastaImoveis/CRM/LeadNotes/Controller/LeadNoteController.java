package com.VastaImoveis.CRM.LeadNotes.Controller;

import com.VastaImoveis.CRM.LeadNotes.Entity.dto.LeadNoteRequestDTO;
import com.VastaImoveis.CRM.LeadNotes.Entity.dto.LeadNoteResponseDTO;
import com.VastaImoveis.CRM.LeadNotes.mapper.LeadNoteMapper;
import com.VastaImoveis.CRM.LeadNotes.service.LeadNoteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/leadNotes")
public class LeadNoteController {
    private final LeadNoteService service;

    public LeadNoteController(LeadNoteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<LeadNoteResponseDTO> create(
        @RequestBody @Valid LeadNoteRequestDTO dto){
            LeadNoteResponseDTO created = service.create(dto);
            return ResponseEntity.ok(created);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<LeadNoteResponseDTO>> findByLeadId(
        @PathVariable UUID userId,
        Pageable pageable
    ){
        Page<LeadNoteResponseDTO> page = service.findByLeadId(userId, pageable);
        return ResponseEntity.ok(page);
    }


}


