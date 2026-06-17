package com.VastaImoveis.CRM.Alert.controller;

import com.VastaImoveis.CRM.Alert.Service.AlertService;
import com.VastaImoveis.CRM.Alert.entity.domain.Alert;
import com.VastaImoveis.CRM.Alert.entity.dto.AlertRequestDTO;
import com.VastaImoveis.CRM.Alert.entity.dto.AlertResponseDTO;
import com.VastaImoveis.CRM.shared.utils.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/Alerts")
public class AlertController {
        private final AlertService service;

        public AlertController(AlertService service) {
            this.service = service;
        }

        @GetMapping("/{userId}")
        @PreAuthorize("hasAnyRole('GERENTE','CORRETOR')")
        public ResponseEntity<ApiResponse<Page<AlertResponseDTO>>> findByUser(
                @PathVariable UUID userId,
                @PageableDefault(size = 15)Pageable pageable
        ){
                Page<AlertResponseDTO> page = service.findByUserId(userId, pageable);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ApiResponse<>(true, page, "Alerts buscados com sucesso")
                );
        }

        @PostMapping
        @PreAuthorize("hasAnyRole('GERENTE','CORRETOR')")
        public ResponseEntity<ApiResponse<AlertResponseDTO>> createAlert(@RequestParam AlertRequestDTO dto){
                AlertResponseDTO created = service.create(dto);

                return ResponseEntity.status(HttpStatus.CREATED).body(
                        new ApiResponse<>(true, created, "Alert criado com sucesso")
                );
        }

        @PatchMapping("/{id}")
        @PreAuthorize("hasAnyRole('GERENTE','CORRETOR')")
        public ResponseEntity<ApiResponse<AlertResponseDTO>> patchRead(@RequestParam Boolean read, @PathVariable UUID alertId){
            AlertResponseDTO alert = service.patchRead(alertId, read);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse<>(true, alert, "Alert alterado com sucesso")
            );
        }

        @DeleteMapping("/{id}")
        @PreAuthorize("hasAnyRole('GERENTE','CORRETOR')")
        public ResponseEntity<ApiResponse<Void>> deleteAlert(@PathVariable UUID alertId){
            service.delete(alertId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new ApiResponse<>(true, null, "Alert deletado com sucesso")
            );
        }

}
