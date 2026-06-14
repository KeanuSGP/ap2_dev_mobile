package com.keanusantos.ap2_dev_mobile.controller;

import com.keanusantos.ap2_dev_mobile.entity.Pagamento;
import com.keanusantos.ap2_dev_mobile.service.PagamentoService;
import com.keanusantos.ap2_dev_mobile.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    @Autowired
    private PagamentoService pagamentoService;
    @Autowired
    private TransacaoService transacaoService;


    @GetMapping(value = "/{id}")
    public Pagamento findById(@PathVariable UUID id) {
        return pagamentoService.findById(id);
    }

    @GetMapping
    public List<Pagamento> findAll() {
        return pagamentoService.findAll();
    }

    @PostMapping
    public ResponseEntity<Pagamento> pagar(@RequestBody Map<String, UUID> dados) {
        pagamentoService.pagar(dados.get("transacaoId"), dados.get("contaFinanceiraId"));
        return  ResponseEntity.status(HttpStatus.CREATED).body(pagamentoService.findById(dados.get("transacaoId")));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity estornar(@PathVariable  UUID id) {
        pagamentoService.estornar(id);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
