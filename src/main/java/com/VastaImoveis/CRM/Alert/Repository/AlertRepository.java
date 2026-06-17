package com.VastaImoveis.CRM.Alert.Repository;

import com.VastaImoveis.CRM.Alert.entity.domain.Alert;
import com.VastaImoveis.CRM.Users.Entity.Domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlertRepository extends JpaRepository<Alert, UUID> {
    Page<Alert> findByUser(User user, Pageable pageable);
}
