package com.keanusantos.ap2_dev_mobile.controller;

import com.keanusantos.ap2_dev_mobile.dto.CategoriaResumoDTO;
import com.keanusantos.ap2_dev_mobile.entity.Categoria;
import com.keanusantos.ap2_dev_mobile.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public List<Categoria> findAll(){
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public Categoria findById(@PathVariable UUID id){
        return service.findById(id);
    }

    @GetMapping(value = "/categoria/{categoria}")
    public Categoria findByNome(@PathVariable String categoria){
        return service.findByNome(categoria);
    }

    @GetMapping(value = "/resumo")
    public List<CategoriaResumoDTO> findById(){
        return service.findAllResumido();
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Categoria categoria){
        Categoria categoriaCriada = service.create(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCriada);
    }

    @PutMapping(value = "/{id}")
    public Categoria update(@PathVariable UUID id, @RequestBody Categoria categoria){
        return service.update(id, categoria);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteById(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
