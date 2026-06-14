package com.keanusantos.ap2_dev_mobile.service;

import com.keanusantos.ap2_dev_mobile.Config.AuthUtils;
import com.keanusantos.ap2_dev_mobile.entity.ContaFinanceira;
import com.keanusantos.ap2_dev_mobile.entity.Pagamento;
import com.keanusantos.ap2_dev_mobile.entity.Transacao;
import com.keanusantos.ap2_dev_mobile.entity.Usuario;
import com.keanusantos.ap2_dev_mobile.repository.ContaFinanceiraRepository;
import com.keanusantos.ap2_dev_mobile.repository.PagamentoRepository;
import com.keanusantos.ap2_dev_mobile.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private ContaFinanceiraRepository contaFinanceiraRepo;

    @Autowired
    private TransacaoRepository transacaoRepo;

    @Autowired
    private AuthUtils authUtils;

    public Pagamento findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public List<Pagamento> findAll() {
        Usuario usuario = authUtils.pegarUsuarioAutenticado();
        return repository.findAllByUsuarioId(usuario.getId());
    }


    @Transactional
    public Pagamento pagar(UUID transacaoId, UUID contaFinanceiraId) {
        Usuario usuario = authUtils.pegarUsuarioAutenticado();
        Transacao transacao = transacaoRepo.findById(transacaoId).orElse(null);
        ContaFinanceira cf = contaFinanceiraRepo.findById(contaFinanceiraId).orElse(null);
        cf.pagar(transacao);
        transacaoRepo.save(transacao);
        contaFinanceiraRepo.save(cf);
        return repository.save(new Pagamento(null, Instant.now(), transacao, cf, usuario));
    }

    public void estornar(UUID id) {
        Pagamento pg = findById(id);
        ContaFinanceira cf = pg.getContaFinanceira();
        Transacao transacao = pg.getTransacao();
        cf.estornar(transacao);
        transacaoRepo.save(transacao);
        contaFinanceiraRepo.save(cf);
        repository.deleteById(id);
    }

}
