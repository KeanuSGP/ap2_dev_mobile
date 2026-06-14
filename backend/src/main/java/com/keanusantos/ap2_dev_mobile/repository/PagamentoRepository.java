package com.keanusantos.ap2_dev_mobile.repository;

import com.keanusantos.ap2_dev_mobile.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {
    List<Pagamento> findAllByUsuarioId(UUID id);
    List<Pagamento> findTop2ByOrderByDataPagamentoDesc();
}
