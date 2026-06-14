package com.keanusantos.ap2_dev_mobile.service;

import com.keanusantos.ap2_dev_mobile.Config.AuthUtils;
import com.keanusantos.ap2_dev_mobile.dto.ContaFinanceiraResumoDTO;
import com.keanusantos.ap2_dev_mobile.entity.ContaFinanceira;
import com.keanusantos.ap2_dev_mobile.entity.Transacao;
import com.keanusantos.ap2_dev_mobile.entity.Usuario;
import com.keanusantos.ap2_dev_mobile.repository.ContaFinanceiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContaFinanceiraService {

    @Autowired
    private ContaFinanceiraRepository repository;
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthUtils authUtils;

    public ContaFinanceira findById(UUID id) {
        Usuario usuario = authUtils.pegarUsuarioAutenticado();
        return repository.findByIdAndUsuarioId(id, usuario.getId()).orElseThrow();
    }

    public List<ContaFinanceira> findAllByUser() {
        Usuario usuario = authUtils.pegarUsuarioAutenticado();
        return repository.findAllByUsuarioId(usuario.getId());
    }

    public List<ContaFinanceiraResumoDTO> findAllResumido() {
        return findAllByUser().stream().map(ContaFinanceiraResumoDTO::mapToContaFinanceiraResumoDTO).toList();
    }

    public ContaFinanceira create(ContaFinanceira data) {
        Usuario usuario = usuarioService.findMe();
        return repository.save(new ContaFinanceira(null, data.getNome(), data.getSaldo(), usuario));
    }

    public ContaFinanceira update(UUID id, ContaFinanceira data) {
        ContaFinanceira conta = repository.findById(id).orElseThrow();
        conta.setNome(data.getNome());
        conta.setSaldo(data.getSaldo());
        return repository.save(conta);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

}
