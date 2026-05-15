package com.VastaImoveis.CRM.Requisitos.Controller;

import com.VastaImoveis.CRM.Requisitos.Entity.Domain.StatusRequisito;
import com.VastaImoveis.CRM.Requisitos.Entity.dto.RequisitoRequestDTO;
import com.VastaImoveis.CRM.Requisitos.Entity.dto.RequisitoResponseDTO;
import com.VastaImoveis.CRM.Requisitos.Service.RequisitosService;
import com.VastaImoveis.CRM.shared.utils.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/requisito")
public class RequisitoController {

    private final RequisitosService service;

    public RequisitoController(RequisitosService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE', 'CORRETOR')")
    public ResponseEntity<ApiResponse<Page<RequisitoResponseDTO>>> findAll(Pageable pageable){
        Page<RequisitoResponseDTO> page = service.findAll(pageable);

        return ResponseEntity.ok(
                new ApiResponse<>(true, page, "Requisitos listados com sucesso")
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('GERENTE','CORRETOR')")
    public ResponseEntity<ApiResponse<RequisitoResponseDTO>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, service.findById(id), "Requisito encontrado com sucesso")
        );
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<ApiResponse<Page<RequisitoResponseDTO>>> findByUserId(@PathVariable UUID userId, Pageable pageable){

        return ResponseEntity.ok(
                new ApiResponse<>(true, service.findAllByUserId(userId, pageable), "Requisitos encontrados com sucesso")
        );
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE', 'CORRETOR')")
    public ResponseEntity<ApiResponse<RequisitoResponseDTO>> create(RequisitoRequestDTO dto){
        return ResponseEntity.ok(
                new ApiResponse<>(true, service.create(dto), "Requisito criado com sucesso"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<ApiResponse<RequisitoResponseDTO>> update(@PathVariable UUID id, RequisitoRequestDTO dto){
        return ResponseEntity.ok(
                new ApiResponse<>(true, service.update(id, dto), "Requisito alterado com sucesso")
        );
    }

    @PutMapping("/status/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<ApiResponse<RequisitoResponseDTO>> updateStatus(@PathVariable UUID id, StatusRequisito status){
        return ResponseEntity.ok(
                new ApiResponse<>(true, service.updateStatus(id, status), "Requisito alterado com sucesso")
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<ApiResponse<RequisitoResponseDTO>> Delete(@PathVariable UUID id){
        service.Delete(id);
        return ResponseEntity.ok(
                new ApiResponse<>(true, null, "Requisito Deletado com sucesso")
        );
    }
}
