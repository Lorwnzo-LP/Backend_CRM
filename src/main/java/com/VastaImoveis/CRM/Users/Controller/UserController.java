package com.VastaImoveis.CRM.Users.Controller;

import com.VastaImoveis.CRM.Users.Entity.dto.UserRequestDTO;
import com.VastaImoveis.CRM.Users.Entity.dto.UserResponseDTO;
import com.VastaImoveis.CRM.Users.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(summary = "Criar usuário")
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid UserRequestDTO dto
    ) {
        return ResponseEntity.ok(service.update(id, dto));
    }
}