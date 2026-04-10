package com.VastaImoveis.CRM.Repository;

import com.VastaImoveis.CRM.Entity.Domain.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LeadRepository extends JpaRepository<Lead, UUID> {

    Optional<Lead> findByEmail(String email);

    boolean existsByEmail(String email);
}