package com.VastaImoveis.CRM.Auth;

import com.VastaImoveis.CRM.Auth.dto.AuthRequestDTO;
import com.VastaImoveis.CRM.Auth.dto.AuthResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO dto) {
        String email = dto.getEmail().toLowerCase().trim();
        String token = service.login(email, dto.getPassword());
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
