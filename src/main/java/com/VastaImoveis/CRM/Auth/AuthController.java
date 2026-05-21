package com.VastaImoveis.CRM.Auth;

import com.VastaImoveis.CRM.Auth.dto.AuthRequestDTO;
import com.VastaImoveis.CRM.Auth.dto.AuthResponseDTO;
import com.VastaImoveis.CRM.Auth.dto.AuthResult;
import com.VastaImoveis.CRM.Users.Entity.Domain.User;
import com.VastaImoveis.CRM.Users.Entity.dto.UserResponseDTO;
import com.VastaImoveis.CRM.Users.mapper.userMapper;
import com.VastaImoveis.CRM.shared.utils.ApiResponse;
import com.VastaImoveis.CRM.shared.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@RequestBody AuthRequestDTO dto) {
        String email = dto.getEmail().toLowerCase().trim();
        AuthResult result = service.login(email, dto.getPassword());

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(
                        new ApiResponse<>(
                                true,
                                new AuthResponseDTO(userMapper.toDTO(result.user()), result.token()),
                                "Login realizado com sucesso"
                        )
                );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDTO>> me() {
        User user = SecurityUtils.getCurrentUser();

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(
                        new ApiResponse<>(
                                true,
                                userMapper.toDTO(user),
                                "Usuário autenticado"
                        )
                );
    }
}
