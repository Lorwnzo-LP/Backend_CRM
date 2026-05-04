package com.VastaImoveis.CRM.Users.Service;

import com.VastaImoveis.CRM.Exception.BusinessException;
import com.VastaImoveis.CRM.Exception.ResourceNotFoundException;
import com.VastaImoveis.CRM.Users.Entity.Domain.RoleUsers;
import com.VastaImoveis.CRM.Users.Entity.Domain.User;
import com.VastaImoveis.CRM.Users.Entity.dto.UserRequestDTO;
import com.VastaImoveis.CRM.Users.Entity.dto.UserResponseDTO;
import com.VastaImoveis.CRM.Users.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.VastaImoveis.CRM.Users.mapper.userMapper.toDTO;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository,
                       PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserResponseDTO create(UserRequestDTO dto) {
        String email = dto.getEmail().toLowerCase().trim();
        if (repository.existsByEmail(email)) {
            throw new BusinessException("Email já cadastrado");
        }

        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole() != null ? dto.getRole() : RoleUsers.CORRETOR);

        return toDTO(repository.save(user));
    }

    public UserResponseDTO update(UUID id, UserRequestDTO dto) {

        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        // 🔥 Normalização
        String email = dto.getEmail().toLowerCase().trim();

        // 🔥 Verifica duplicidade (se mudou o email)
        if (!user.getEmail().equals(email) &&
                repository.existsByEmail(email)) {
            throw new BusinessException("Email já cadastrado");
        }

        // 🔥 Atualizações seguras
        user.setNome(dto.getNome());
        user.setEmail(email);

        // ⚠️ Senha: só atualiza se vier preenchida
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        // ⚠️ Role: opcional (depende da sua regra de negócio)
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }

        return toDTO(repository.save(user));
    }

}