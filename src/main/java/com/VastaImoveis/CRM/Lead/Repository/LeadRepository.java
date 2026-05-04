package com.VastaImoveis.CRM.Lead.Repository;

import com.VastaImoveis.CRM.Lead.Entity.Domain.Lead;
import com.VastaImoveis.CRM.Lead.Entity.dto.StatusCount;
import com.VastaImoveis.CRM.Users.Entity.Domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LeadRepository extends JpaRepository<Lead, UUID> {

    Optional<Lead> findByEmail(String email);

    Page<Lead> findByUser(User user, Pageable pageable);

    Page<Lead> findByUserId(UUID userId, Pageable pageable);

    Page<Lead> findByUserIn(List<User> users, Pageable pageable);

    boolean existsByEmail(String email);

    @Query("""

                 SELECT new com.VastaImoveis.CRM.Lead.Entity.dto.StatusCount(l.status, COUNT(l))
                FROM Lead l
                WHERE (:user IS NULL OR l.user = :user)
                GROUP BY l.status
            """)
    List<StatusCount> countByStatus(User user);



    /*
    Leads por periodo:
        WHERE (:user IS NULL OR l.user = :user)
        AND l.createdAt BETWEEN :start AND :end

    Leads por mês
        SELECT FUNCTION('MONTH', l.createdAt), COUNT(l)

    Ranking de corretores
        SELECT l.user.nome, COUNT(l)
            FROM Lead l
            GROUP BY l.user.nome

    Conversão (taxa de fechamento)
            long fechados = porStatus.getOrDefault("FECHADO", 0L);
            double taxa = total > 0 ? (double) fechados / total : 0;
        */


}