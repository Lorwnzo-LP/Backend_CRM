package com.VastaImoveis.CRM.Controller;

import com.VastaImoveis.CRM.Entity.dto.LeadRequestDTO;
import com.VastaImoveis.CRM.Entity.dto.LeadResponseDTO;
import com.VastaImoveis.CRM.Service.LeadService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/leads")
public class LeadController {

    private final LeadService service;

    public LeadController(LeadService service) {
        this.service = service;
    }

    // 🔥 Criar Lead
    @PostMapping
    public ResponseEntity<LeadResponseDTO> create(
            @RequestBody @Valid LeadRequestDTO dto) {

        LeadResponseDTO created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    // 📄 Listar com paginação
    @GetMapping
    public ResponseEntity<Page<LeadResponseDTO>> findAll(Pageable pageable) {
        Page<LeadResponseDTO> page = service.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    // 🔍 Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<LeadResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // ✏️ Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<LeadResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid LeadRequestDTO dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }

    // ❌ Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
