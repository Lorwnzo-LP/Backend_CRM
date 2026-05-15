package com.VastaImoveis.CRM;

import com.VastaImoveis.CRM.Users.Entity.Domain.RegiaoUsers;
import com.VastaImoveis.CRM.Users.Entity.Domain.RoleUsers;
import com.VastaImoveis.CRM.Users.Entity.Domain.User;
import com.VastaImoveis.CRM.Users.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {

        return args -> {
            if (repo.count() == 0) {
                User user = new User();
                user.setNome("Admin");
                user.setEmail("admin@email.com");
                user.setPassword(encoder.encode("123456"));
                user.setRole(RoleUsers.GERENTE);
                user.setRegiao(RegiaoUsers.CURITIBA);
                System.out.println(user.getNome());
                System.out.println(user.getRole());
                repo.save(user);
            }
        };
    }

}
