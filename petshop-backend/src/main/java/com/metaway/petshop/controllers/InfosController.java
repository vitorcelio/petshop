package com.metaway.petshop.controllers;

import com.metaway.petshop.configurations.security.RoleAdmin;
import com.metaway.petshop.configurations.security.RoleCustomer;
import com.metaway.petshop.configurations.swagger.AuthorizationSwagger;
import com.metaway.petshop.dto.response.InfosResponseDTO;
import com.metaway.petshop.models.User;
import com.metaway.petshop.services.infos.InfosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/infos")
@AuthorizationSwagger
public class InfosController {

    private final InfosService service;

    @RoleAdmin
    @GetMapping("/admin")
    public ResponseEntity<InfosResponseDTO> getInfosAdmin() {
        return new ResponseEntity<>(service.getInfosAdmin(), HttpStatus.OK);
    }

    @RoleCustomer
    @GetMapping("/customer")
    public ResponseEntity<InfosResponseDTO> getInfosCustomer(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(service.getInfosCustomer(user.getId()), HttpStatus.OK);
    }

}
