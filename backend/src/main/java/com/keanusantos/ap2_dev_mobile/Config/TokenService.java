package com.keanusantos.ap2_dev_mobile.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.keanusantos.ap2_dev_mobile.entity.Usuario;
import org.springframework.stereotype.Service;
import tools.jackson.databind.node.StringNode;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class TokenService {
    public String gerarToken(Usuario usuario) {
        try {
            Algorithm assinatura = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withIssuer("ap2")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(gerarExpiracao())
                    .sign(assinatura);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar Token", exception);
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm assinatura = Algorithm.HMAC256("secret");
            return JWT.require(assinatura)
                    .withIssuer("ap2")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant gerarExpiracao () {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
