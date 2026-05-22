package com.VastaImoveis.CRM.Lead.Controller;

import com.VastaImoveis.CRM.Lead.Entity.dto.LeadDashboardDTO;
import com.VastaImoveis.CRM.Lead.Entity.dto.LeadRequestDTO;
import com.VastaImoveis.CRM.Lead.Entity.dto.LeadResponseDTO;
import com.VastaImoveis.CRM.Lead.Service.LeadService;
import com.VastaImoveis.CRM.shared.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/leads")
public class LeadController {

    private final LeadService service;

    public LeadController(LeadService service) {
        this.service = service;
    }


    // 📄 Listar com paginação
    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE','CORRETOR')")
    public ResponseEntity<ApiResponse<Page<LeadResponseDTO>>> findAll(Pageable pageable) {
        Page<LeadResponseDTO> page = service.findAllWithPage(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                    new ApiResponse<>(true, page, "Leads listados com sucesso")
                );
    }

    @GetMapping("/all/{userId}")
    @PreAuthorize("hasAnyRole('GERENTE', 'CORRETOR')")
    public ResponseEntity<ApiResponse<List<LeadResponseDTO>>> findAllByUserIdList(@PathVariable UUID userId){
        List<LeadResponseDTO> leads = service.findAllByUserIdList(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(true, leads, "Leads buscados com sucesso")
                );
    }

    // 🔍 Buscar por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE','CORRETOR')")
    public ResponseEntity<ApiResponse<LeadResponseDTO>> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                    new ApiResponse<>(true, service.findById(id), "Lead buscado com sucesso")
        );
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('GERENTE')")
    public ResponseEntity<ApiResponse<Page<LeadResponseDTO>>> findByUserId(@PathVariable UUID userId, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                    new ApiResponse<>(true, service.findAllByUser(userId, pageable), "Leads listados com sucesso")
        );
    }

    // Buscar em dashboard
    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('GERENTE','CORRETOR')")
    public ResponseEntity<ApiResponse<LeadDashboardDTO>> dashboard() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                    new ApiResponse<>(true, service.getDashboard(), "Dashboard buscado com sucesso")
        );
    }

    // 🔥 Criar Lead
    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE','CORRETOR')")
    public ResponseEntity<ApiResponse<LeadResponseDTO>> create(
            @RequestBody @Valid LeadRequestDTO dto) {
        LeadResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    new ApiResponse<>(true, created, "Lead criado com sucesso")
        );
    }

    // ✏️ Atualizar
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE','CORRETOR')")
    public ResponseEntity<ApiResponse<LeadResponseDTO>> update(
            @PathVariable UUID id,
            @RequestBody @Valid LeadRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponse<>(true, service.update(id, dto), "Lead atualizado com sucesso"));
    }

    // ❌ Deletar
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                new ApiResponse<>(true, null, "Lead deletado com sucesso")
        );
    }


}
