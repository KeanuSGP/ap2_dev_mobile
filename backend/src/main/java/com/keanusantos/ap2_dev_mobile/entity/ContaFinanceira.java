package com.keanusantos.ap2_dev_mobile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.keanusantos.ap2_dev_mobile.entity.enums.Status;
import com.keanusantos.ap2_dev_mobile.entity.enums.Tipo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "contas_financeiras")
public class ContaFinanceira {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotEmpty
    private String nome;

    @NotNull
    private Float saldo;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "contaFinanceira")
    private List<Transacao> transacoes;

    public ContaFinanceira() {}

    public ContaFinanceira(UUID id, String nome, Float saldo, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.saldo = saldo;
        this.usuario = usuario;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }

    public void pagar(Transacao transacao) {
        Float quantia = transacao.getTotal();
        if (transacao.getTipo() == Tipo.DESPESA) {
            if (quantia > saldo) throw new RuntimeException("Saldo insuficiente");
            transacao.serPaga();
            saldo -= quantia;
        } else {
            transacao.serPaga();
            saldo += quantia;

        }

    }

    public void estornar(Transacao transacao) {
        if (transacao.getStatus() != Status.QUITADO) throw new RuntimeException("A transação não está quitada.");

        Float quantia = transacao.getTotal();
        if (transacao.getTipo() == Tipo.DESPESA) {
            saldo += quantia;
            transacao.serEstornada();
        } else {
            if (saldo < quantia) {
                throw new RuntimeException("Saldo insuficiente");
            } else {
                saldo -= quantia;
                transacao.serEstornada();
            }
        }

    }
}
