package com.VastaImoveis.CRM.Lead.Repository;

import com.VastaImoveis.CRM.Lead.Entity.Domain.Lead;
import com.VastaImoveis.CRM.Users.Entity.Domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LeadRepository extends JpaRepository<Lead, UUID> {

    Optional<Lead> findByEmail(String email);

    Page<Lead> findByUser(User user, Pageable pageable);

    Page<Lead> findByUserIn(List<User> users, Pageable pageable);

    boolean existsByEmail(String email);
}