package com.keanusantos.ap2_dev_mobile.entity;

import com.keanusantos.ap2_dev_mobile.entity.enums.Status;
import com.keanusantos.ap2_dev_mobile.entity.enums.Tipo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "transacoes")
public class Transacao {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotNull
    private Instant criadoEm;

    @NotEmpty(message = "PREENCHA O NOME DO ITEM COMPRADO")
    private String item;

    @NotEmpty(message = "PREENCHA A DESCRIÇÃO DA TRANSAÇÃO")
    @Column(length = 500)
    private String descricao;

    @NotNull(message = "A DATA DA COMPRA NÃO PODE FICAR VAZIA")
    private LocalDate dataCompra;

    @NotNull(message = "O VENCIMENTO NÃO PODE FICAR VAZIO")
    private LocalDate dataVencimento;

    @NotNull(message = "PREENCHA O VALOR TOTAL DA COMPRA")
    @Min(0)
    private Float total;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "INSIRA O TIPO DA COMPRA")
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "DEFINA UMA CATEGORIA PARA A TRANSAÇÃO")
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "DEFINA UMA CONTA FINANCEIRA PARA A TRANSAÇÃO")
    private ContaFinanceira contaFinanceira;

    @NotNull(message = "DEFINA A QUAL USUARIO A CONTA PERTENCE")
    @ManyToOne
    private Usuario usuario;

    public Transacao() {}

    public Transacao(UUID id, Instant criadoEm, String item, String descricao, LocalDate dataCompra, LocalDate dataVencimento, Float total, Status status, Tipo tipo, Categoria categoria, ContaFinanceira cf, Usuario usuario) {
        this.id = id;
        this.criadoEm = criadoEm;
        this.item = item;
        this.descricao = descricao;
        this.dataCompra = dataCompra;
        this.dataVencimento = dataVencimento;
        this.total = total;
        this.status = status;
        this.tipo = tipo;
        this.categoria = categoria;
        this.contaFinanceira = cf;
        this.usuario = usuario;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Instant criadaEm) {
        this.criadoEm = criadaEm;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public ContaFinanceira getContaFinanceira() {
        return contaFinanceira;
    }

    public void setContaFinanceira(ContaFinanceira contaFinanceira) {
        this.contaFinanceira = contaFinanceira;
    }

    public void serPaga() {
        if (this.status != Status.ABERTO) throw new RuntimeException("A transação já está quitada.");
        this.status = Status.QUITADO;
    }

    public void serEstornada() {
        if (this.status != Status.QUITADO) {
            throw new RuntimeException("A transação não está paga");
        }
        this.status = Status.ABERTO;
    }
}
