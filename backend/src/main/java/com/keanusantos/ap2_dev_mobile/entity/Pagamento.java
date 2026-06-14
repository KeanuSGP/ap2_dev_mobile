package com.keanusantos.ap2_dev_mobile.entity;

import com.keanusantos.ap2_dev_mobile.service.ContaFinanceiraService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pagamentos")
public class Pagamento {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotNull(message = "DEFINA A DATA DO PAGAMENTO")
    private Instant dataPagamento;

    @OneToOne
    @NotNull(message = "DEFINA A TRANSAÇÃO A SER PAGA")
    private Transacao transacao;

    @NotNull
    @ManyToOne
    private ContaFinanceira contaFinanceira;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    public Pagamento() {}

    public Pagamento(UUID id, Instant dataPagamento, Transacao transacao, ContaFinanceira cf,  Usuario usuario) {
        this.id = id;
        this.dataPagamento = dataPagamento;
        this.transacao = transacao;
        this.contaFinanceira = cf;
        this.usuario = usuario;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Instant dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Transacao getTransacao() {
        return transacao;
    }

    public void setTransacao(Transacao transacao) {
        this.transacao = transacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ContaFinanceira getContaFinanceira() {
        return contaFinanceira;
    }

    public void setContaFinanceira(ContaFinanceira contaFinanceira) {
        this.contaFinanceira = contaFinanceira;
    }
}
