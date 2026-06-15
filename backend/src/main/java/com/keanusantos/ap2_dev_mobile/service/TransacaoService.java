package com.keanusantos.ap2_dev_mobile.service;

import com.keanusantos.ap2_dev_mobile.Config.AuthUtils;
import com.keanusantos.ap2_dev_mobile.dto.TransacaoTelaInicialDTO;
import com.keanusantos.ap2_dev_mobile.entity.Categoria;
import com.keanusantos.ap2_dev_mobile.entity.ContaFinanceira;
import com.keanusantos.ap2_dev_mobile.entity.Transacao;
import com.keanusantos.ap2_dev_mobile.entity.Usuario;
import com.keanusantos.ap2_dev_mobile.entity.enums.Status;
import com.keanusantos.ap2_dev_mobile.entity.enums.Tipo;
import com.keanusantos.ap2_dev_mobile.repository.PagamentoRepository;
import com.keanusantos.ap2_dev_mobile.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ContaFinanceiraService contaFinanceiraService;

    @Autowired
    private PagamentoRepository pagamentoRepository;


    @Autowired
    private AuthUtils auth;

    public Transacao findById(UUID id){
        return repository.findById(id).orElse(null);
    }

    public List<Transacao> findAll(){
        Usuario usuario = auth.pegarUsuarioAutenticado();
        return repository.findAllByUsuarioId(usuario.getId());
    }

    public List<Transacao> findAllByDataCompraBetweenAndTipoAndStatus(LocalDate inicio, LocalDate fim, Tipo tipo, Status status) {
        return repository.findAllByDataCompraBetweenAndTipoAndStatus(inicio, fim, tipo, status);
    }

    public List<TransacaoTelaInicialDTO> ultimosLancamentos() {

        return pagamentoRepository.findTop2ByOrderByDataPagamentoDesc().stream().map(p -> TransacaoTelaInicialDTO.mapToTransacaoTelaInicialDTOComPagamento(p.getTransacao(), p)).toList();
    }

    public List<TransacaoTelaInicialDTO> transacoesResumidas() {
        return findAll().stream().map(TransacaoTelaInicialDTO::mapToTransacaoTelaInicialDTO).toList();
    }

    public Transacao create(Transacao data){
        Usuario usuario = auth.pegarUsuarioAutenticado();
        Categoria categoria = categoriaService.findById(data.getCategoria().getId());
        ContaFinanceira cf = contaFinanceiraService.findById(data.getContaFinanceira().getId());

        return repository.save(
                new Transacao(null, Instant.now(), data.getItem(), data.getDescricao(), data.getDataCompra(),
                data.getDataVencimento(), data.getTotal(), Status.ABERTO, data.getTipo(), categoria, cf, usuario)
        );
    }

    public Transacao update(UUID id, Transacao data){
        Transacao transacao = findById(id);
        transacao.setItem(data.getItem());
        transacao.setDescricao(data.getDescricao());
        transacao.setDataCompra(data.getDataCompra());
        transacao.setDataVencimento(data.getDataVencimento());
        transacao.setTotal(data.getTotal());
        return repository.save(transacao);
    }

    public void delete(UUID id){
        repository.deleteById(id);
    }
}
