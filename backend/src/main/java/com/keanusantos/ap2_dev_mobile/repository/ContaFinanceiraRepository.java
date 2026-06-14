package com.keanusantos.ap2_dev_mobile.repository;

import com.keanusantos.ap2_dev_mobile.entity.ContaFinanceira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContaFinanceiraRepository extends JpaRepository<ContaFinanceira, UUID> {
    List<ContaFinanceira> findAllByUsuarioId(UUID id);
    Optional<ContaFinanceira> findByIdAndUsuarioId(UUID id, UUID usuarioId);
}
