package com.keanusantos.ap2_dev_mobile.service;

import com.keanusantos.ap2_dev_mobile.Config.AuthUtils;
import com.keanusantos.ap2_dev_mobile.entity.Usuario;
import com.keanusantos.ap2_dev_mobile.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private AuthUtils auth;

    public Usuario findMe() {
        Usuario usuario = repository.findById(auth.pegarUsuarioAutenticado().getId()).orElse(null);
        return usuario;
    }


    public Usuario create(Usuario data) {
        return repository.save(new Usuario(null, data.getNome(), data.getEmail(), data.getSenha()));
    }

    public Usuario update(Usuario data) {
        Usuario usuario = findMe();
        usuario.setNome(data.getNome());
        usuario.setEmail(data.getEmail());
        usuario.setSenha(data.getSenha());
        return repository.save(usuario);
    }

    public void deleteMe() {
        Usuario usuario = findMe();
        repository.deleteById(usuario.getId());
    }
}
