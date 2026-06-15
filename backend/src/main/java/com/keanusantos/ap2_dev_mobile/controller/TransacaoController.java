package com.keanusantos.ap2_dev_mobile.controller;

import com.keanusantos.ap2_dev_mobile.dto.TransacaoTelaInicialDTO;
import com.keanusantos.ap2_dev_mobile.entity.Transacao;
import com.keanusantos.ap2_dev_mobile.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @GetMapping
    public List<Transacao> findAll(){
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public Transacao findById(@PathVariable UUID id){
        return service.findById(id);
    }

    @GetMapping(value = "/resumo")
    public List<TransacaoTelaInicialDTO> findAllTransacoesResumidas() {
        return service.transacoesResumidas();
    }

    @PostMapping
    public ResponseEntity<Transacao> create(@RequestBody Transacao transacao){
        Transacao transacaoCriada = service.create(transacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoCriada);
    }


    @PutMapping(value = "/{id}")
    public Transacao update(@PathVariable UUID id, @RequestBody Transacao transacao){
        return service.update(id, transacao);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Transacao> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
