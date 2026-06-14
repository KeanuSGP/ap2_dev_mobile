package com.keanusantos.ap2_dev_mobile.dto;

import com.keanusantos.ap2_dev_mobile.entity.Pagamento;
import com.keanusantos.ap2_dev_mobile.entity.Transacao;
import com.keanusantos.ap2_dev_mobile.entity.enums.Status;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record TransacaoTelaInicialDTO(
        UUID id, String item, Float valor, LocalDate vencimento, Instant dataPagamento, Status status
) {

    public static TransacaoTelaInicialDTO mapToTransacaoTelaInicialDTO(Transacao transacao) {
        return new TransacaoTelaInicialDTO(
                transacao.getId(),
                transacao.getItem(),
                transacao.getTotal(),
                transacao.getDataVencimento(),
                null,
                transacao.getStatus()
        );
    }

    public static TransacaoTelaInicialDTO mapToTransacaoTelaInicialDTOComPagamento(Transacao transacao, Pagamento pagamento) {
        return new TransacaoTelaInicialDTO(
                transacao.getId(),
                transacao.getItem(),
                transacao.getTotal(),
                transacao.getDataVencimento(),
                pagamento.getDataPagamento(),
                transacao.getStatus()
        );
    }

}
