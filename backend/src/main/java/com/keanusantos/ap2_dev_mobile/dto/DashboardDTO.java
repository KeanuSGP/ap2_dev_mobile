package com.keanusantos.ap2_dev_mobile.dto;

import com.keanusantos.ap2_dev_mobile.entity.Transacao;

import java.util.List;

public record DashboardDTO(
        Float total, Float receitasMes, Float despesasMes, List<TransacaoTelaInicialDTO> ultimasTransacoes
) {
}
