package com.VastaImoveis.CRM.Auth;

import com.VastaImoveis.CRM.Exception.BusinessException;
import com.VastaImoveis.CRM.Auth.Jwt.JwtService;
import com.VastaImoveis.CRM.Users.Entity.Domain.User;
import com.VastaImoveis.CRM.Users.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository repository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       UserRepository userRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(String email, String password) {
        try {
            System.out.println("Tentando login com: " + email);

            User user = repository.findByEmail(email)
                    .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BusinessException("Senha inválida");
            }

            return jwtService.generateToken(user);

        } catch (Exception e) {
            e.printStackTrace(); // 👈 ISSO AQUI É ESSENCIAL AGORA
            throw e;
        }
    }
}
