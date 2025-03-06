package com.metaway.petshop.controllers;

import com.metaway.petshop.dto.request.AuthenticationRequestDTO;
import com.metaway.petshop.dto.response.AuthenticationResponseDTO;
import com.metaway.petshop.services.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody @Validated AuthenticationRequestDTO request) {
        return new ResponseEntity<>(service.login(request), HttpStatus.OK);
    }

}
