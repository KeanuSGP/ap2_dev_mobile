package com.keanusantos.ap2_dev_mobile.service;


import com.keanusantos.ap2_dev_mobile.dto.DashboardDTO;
import com.keanusantos.ap2_dev_mobile.dto.TransacaoTelaInicialDTO;
import com.keanusantos.ap2_dev_mobile.entity.ContaFinanceira;
import com.keanusantos.ap2_dev_mobile.entity.Transacao;
import com.keanusantos.ap2_dev_mobile.entity.enums.Status;
import com.keanusantos.ap2_dev_mobile.entity.enums.Tipo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private ContaFinanceiraService cfService;

    @Autowired
    private TransacaoService transacaoService;

    public DashboardDTO dashboard() {
        List<ContaFinanceira> contas = cfService.findAllByUser();
        Float total = contas.stream().map(conta -> conta.getSaldo()).reduce(0f, Float::sum);
        LocalDate dia = LocalDate.now();
        List<Transacao> receitasMesAtualTransacoes = transacaoService.findAllByDataCompraBetweenAndTipoAndStatus(dia.withDayOfMonth(1), dia.withDayOfMonth(dia.lengthOfMonth()), Tipo.RECEITA, Status.QUITADO);
        List<Transacao> despesasMesAtualTransacoes = transacaoService.findAllByDataCompraBetweenAndTipoAndStatus(dia.withDayOfMonth(1), dia.withDayOfMonth(dia.lengthOfMonth()), Tipo.DESPESA, Status.QUITADO);
        Float receitasMesAtual = 0f;
        Float despesasMesAtual = 0f;
        if (!receitasMesAtualTransacoes.isEmpty()) {
            receitasMesAtual = receitasMesAtualTransacoes.stream().map(Transacao::getTotal).reduce(0f, Float::sum);
        }
        if (!despesasMesAtualTransacoes.isEmpty()) {
            despesasMesAtual = despesasMesAtualTransacoes.stream().map(Transacao::getTotal).reduce(0f, Float::sum);
        }
        List<TransacaoTelaInicialDTO> ultimosLancamentos = transacaoService.ultimosLancamentos();
        return new DashboardDTO(total, receitasMesAtual, despesasMesAtual, ultimosLancamentos);
    }
}
