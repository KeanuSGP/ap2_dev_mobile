package com.keanusantos.ap2_dev_mobile.service;

import com.keanusantos.ap2_dev_mobile.Config.AuthUtils;
import com.keanusantos.ap2_dev_mobile.dto.CategoriaResumoDTO;
import com.keanusantos.ap2_dev_mobile.entity.Categoria;
import com.keanusantos.ap2_dev_mobile.entity.Usuario;
import com.keanusantos.ap2_dev_mobile.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private AuthUtils auth;

    public Categoria findByNome(String nome) {
        return repository.findByNome(nome);
    }

    public Categoria findById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Categoria> findAll() {
        Usuario usuario = auth.pegarUsuarioAutenticado();
        return repository.findAllByUsuarioId(usuario.getId());
    }

    public List<CategoriaResumoDTO> findAllResumido() {
        return findAll().stream().map(CategoriaResumoDTO::mapToCategoriaResumoDTO).toList();
    }

    public Categoria create (Categoria data) {
        Usuario usuario = auth.pegarUsuarioAutenticado();
        return repository.save(new Categoria(null, data.getNome(), data.getCor(), usuario));
    }

    public Categoria update (UUID id, Categoria data) {
        Categoria categoria = findById(id);
        categoria.setNome(data.getNome());
        categoria.setCor(data.getCor());
        return repository.save(categoria);
    }

    public void delete (UUID id) {
        repository.deleteById(id);
    }

}
