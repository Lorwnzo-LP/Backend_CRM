package com.VastaImoveis.CRM.Requisitos.Controller;

import com.VastaImoveis.CRM.Requisitos.Entity.Domain.StatusRequisito;
import com.VastaImoveis.CRM.Requisitos.Entity.dto.RequisitoRequestDTO;
import com.VastaImoveis.CRM.Requisitos.Entity.dto.RequisitoResponseDTO;
import com.VastaImoveis.CRM.Requisitos.Service.RequisitosService;
import com.VastaImoveis.CRM.shared.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/requisitos")
public class RequisitoController {

    private final RequisitosService service;

    public RequisitoController(RequisitosService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('GERENTE', 'CORRETOR')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<RequisitoResponseDTO>>> findAll(Pageable pageable){
        Page<RequisitoResponseDTO> page = service.findAll(pageable);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(
                new ApiResponse<>(true, page, "Requisitos listados com sucesso")
        );
    }

    @PreAuthorize("hasAnyRole('GERENTE','CORRETOR')")
    @GetMapping("id/{id}")
    public ResponseEntity<ApiResponse<RequisitoResponseDTO>> findById(@PathVariable UUID id) {
        RequisitoResponseDTO requisito = service.findById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                new ApiResponse<>(true, requisito, "Requisito encontrado com sucesso")
        );
    }

    @PreAuthorize("hasRole('GERENTE')")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Page<RequisitoResponseDTO>>> findByUserId(@PathVariable UUID userId, Pageable pageable){
            Page<RequisitoResponseDTO> page = service.findAllByUserId(userId, pageable);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                new ApiResponse<>(true, page, "Requisitos encontrados com sucesso")
        );
    }

    @PreAuthorize("hasAnyRole('GERENTE', 'CORRETOR')")
    @PostMapping
    public ResponseEntity<ApiResponse<RequisitoResponseDTO>> create(@RequestBody @Valid RequisitoRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    new ApiResponse<>(true, service.create(dto), "Requisito criado com sucesso"));
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RequisitoResponseDTO>> update(@PathVariable UUID id, @RequestBody @Valid RequisitoRequestDTO dto){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(
                    new ApiResponse<>(true, service.update(id, dto), "Requisito alterado com sucesso")
        );
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PutMapping("/status/{id}")
    public ResponseEntity<ApiResponse<RequisitoResponseDTO>> updateStatus(@PathVariable UUID id, @RequestBody @Valid RequisitoRequestDTO status){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(
                    new ApiResponse<>(true, service.updateStatus(id, status.getStatus()), "Requisito alterado com sucesso")
        );
    }

    @PreAuthorize("hasRole('GERENTE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<RequisitoResponseDTO>> Delete(@PathVariable UUID id){
        service.Delete(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(
                    new ApiResponse<>(true, null, "Requisito Deletado com sucesso")
        );
    }
}
