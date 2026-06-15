package com.keanusantos.ap2_dev_mobile.controller;

import com.keanusantos.ap2_dev_mobile.dto.ContaFinanceiraResumoDTO;
import com.keanusantos.ap2_dev_mobile.entity.ContaFinanceira;
import com.keanusantos.ap2_dev_mobile.service.ContaFinanceiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/contas")
public class ContaFinanceiraController {

    @Autowired
    private ContaFinanceiraService service;

    @GetMapping(value = "/{id}")
    public ContaFinanceira findByIdAndUsuarioId(@PathVariable UUID id) {
        return service.findById(id);
    }

    @GetMapping
    public List<ContaFinanceira> findAllByUsuarioId(){
        return service.findAllByUser();
    }

    @GetMapping(value = "/resumo")
    public List<ContaFinanceiraResumoDTO> findAllResumido(){
        return service.findAllResumido();
    }

    @PostMapping
    public ContaFinanceira create(@RequestBody ContaFinanceira contaFinanceira){
        return service.create(contaFinanceira);
    }

    @PutMapping(value = "/{id}")
    public ContaFinanceira update(@PathVariable UUID id, @RequestBody ContaFinanceira contaFinanceira){
        return service.update(id, contaFinanceira);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable UUID id){
        service.delete(id);
    }
}
