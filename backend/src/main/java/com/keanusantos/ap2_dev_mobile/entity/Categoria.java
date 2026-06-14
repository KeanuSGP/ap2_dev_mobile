package com.keanusantos.ap2_dev_mobile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotEmpty
    private String nome;

    @NotEmpty
    @Size(min = 3, max = 6)
    @Column(unique = true)
    private String cor;

    @NotNull
    @ManyToOne
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria")
    private List<Transacao> transacoes;

    public Categoria() {}

    public Categoria(UUID id, String nome, String cor, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.cor = cor;
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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
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
}
