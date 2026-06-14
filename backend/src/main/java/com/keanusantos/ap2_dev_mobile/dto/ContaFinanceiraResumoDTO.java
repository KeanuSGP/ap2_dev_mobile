package com.keanusantos.ap2_dev_mobile.dto;

import com.keanusantos.ap2_dev_mobile.entity.ContaFinanceira;

import java.util.UUID;

public record ContaFinanceiraResumoDTO(
        UUID id, String nome, Float saldo, Integer lancamentosQtd
) {
    public static ContaFinanceiraResumoDTO mapToContaFinanceiraResumoDTO(ContaFinanceira contaFinanceira) {
        return new ContaFinanceiraResumoDTO(
                contaFinanceira.getId(), contaFinanceira.getNome(), contaFinanceira.getSaldo(), contaFinanceira.getTransacoes().size()
        );
    }
}
