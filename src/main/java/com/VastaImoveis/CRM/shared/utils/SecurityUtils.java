package com.VastaImoveis.CRM.shared.utils;

import com.VastaImoveis.CRM.Users.Entity.Domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityUtils {

    public static User getCurrentUser() {
        return (User) Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getPrincipal();
    }

    public static boolean isGerente(){
        return  getCurrentUser().getRole().name().equals("GERENTE");
    }
}