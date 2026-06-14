package com.keanusantos.ap2_dev_mobile.controller;

import com.keanusantos.ap2_dev_mobile.Config.TokenService;
import com.keanusantos.ap2_dev_mobile.dto.LoginResponseDTO;
import com.keanusantos.ap2_dev_mobile.entity.Usuario;
import com.keanusantos.ap2_dev_mobile.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody Map<String, String> dados) {
        var senhaUsuario = new UsernamePasswordAuthenticationToken(dados.get("email"), dados.get("senha"));
        var auth = this.authenticationManager.authenticate(senhaUsuario);
        Usuario usuario = (Usuario) auth.getPrincipal();
        var token = tokenService.gerarToken(usuario);
        return new LoginResponseDTO(token, usuario);
    }

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> dados) {
        String senhaEncriptada = new BCryptPasswordEncoder().encode(dados.get("senha"));
        Usuario novoUsuario = new Usuario( null, dados.get("nome"), dados.get("email"), senhaEncriptada);
        repository.save(novoUsuario);
        return "Registrado";
    }
}
