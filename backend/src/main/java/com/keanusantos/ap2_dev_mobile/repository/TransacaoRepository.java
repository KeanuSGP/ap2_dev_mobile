package com.keanusantos.ap2_dev_mobile.repository;

import com.keanusantos.ap2_dev_mobile.dto.TransacaoTelaInicialDTO;
import com.keanusantos.ap2_dev_mobile.entity.Transacao;
import com.keanusantos.ap2_dev_mobile.entity.enums.Status;
import com.keanusantos.ap2_dev_mobile.entity.enums.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
    List<Transacao> findAllByUsuarioId(UUID id);
    List<Transacao> findAllByDataCompraBetweenAndTipoAndStatus(LocalDate dataCompra, LocalDate dataVencimento, Tipo tipo, Status status);
    List<Transacao> findTop2ByOrderByCriadoEmDesc();
}
