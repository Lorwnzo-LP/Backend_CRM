package com.VastaImoveis.CRM.Lead.utils;

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
}