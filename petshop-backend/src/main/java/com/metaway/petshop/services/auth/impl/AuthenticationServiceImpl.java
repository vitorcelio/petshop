package com.metaway.petshop.services.auth.impl;

import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.dto.request.AuthenticationRequestDTO;
import com.metaway.petshop.dto.response.AuthenticationResponseDTO;
import com.metaway.petshop.models.User;
import com.metaway.petshop.services.auth.AuthenticationService;
import com.metaway.petshop.services.auth.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Override
    public AuthenticationResponseDTO login(AuthenticationRequestDTO request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.getCpf(), request.getPassword());

        var auth = authenticationManager.authenticate(usernamePassword);
        User user = (User) auth.getPrincipal();

        try {
            String token = tokenService.generateToken(user);
            Date expirationToken = tokenService.expirationToken(token);

            log.info("Login efetuado com sucesso!");
            return new AuthenticationResponseDTO(token, user.getId(), user.getCpf(), expirationToken, user.getRoleId());
        } catch (Exception e) {
            log.error("Erro ao efetuar login: ", e);
            throw new ValidationException("Erro ao fazer login!");
        }
    }

}
