package com.keanusantos.ap2_dev_mobile.controller;

import com.keanusantos.ap2_dev_mobile.entity.Usuario;
import com.keanusantos.ap2_dev_mobile.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping("/me")
    public Usuario findMe() {
        return service.findMe();
    }

    @PostMapping
    public Usuario create(@RequestBody Usuario usuario) {
        return service.create(usuario);
    }

    @PutMapping
    public Usuario update( @RequestBody Usuario usuario) {
        return service.update(usuario);
    }

    @DeleteMapping
    public void delete() {
        service.deleteMe();
    }


}
