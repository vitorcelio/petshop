package com.metaway.petshop.services.auth.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.models.User;
import com.metaway.petshop.services.auth.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String ISSUER = "petshop";

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getCpf())
                    .withClaim("authorities",
                            user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .withExpiresAt(genExpirationDate()).sign(algorithm);
        } catch (Exception e) {
            throw new ValidationException("Erro ao gerar token!");
        }
    }

    @Override
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public Date expirationToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getExpiresAt();
        } catch (Exception e) {
            throw new ValidationException("Erro ao buscar tempo de expiração do token!");
        }
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(5).toInstant(ZoneOffset.of("-03:00"));
    }

}
