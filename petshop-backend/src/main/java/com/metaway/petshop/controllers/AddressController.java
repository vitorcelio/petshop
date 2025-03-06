package com.metaway.petshop.controllers;

import com.metaway.petshop.configurations.security.RoleAdmin;
import com.metaway.petshop.configurations.security.RoleCustomer;
import com.metaway.petshop.configurations.swagger.AuthorizationSwagger;
import com.metaway.petshop.dto.request.AddressRequestDTO;
import com.metaway.petshop.dto.response.AddressResponseDTO;
import com.metaway.petshop.models.User;
import com.metaway.petshop.services.address.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/addresses")
@AuthorizationSwagger
public class AddressController {

    private final AddressService service;

    @RoleCustomer
    @PostMapping
    public ResponseEntity<AddressResponseDTO> save(@RequestBody @Validated AddressRequestDTO request) {
        return new ResponseEntity<>(service.save(request), HttpStatus.CREATED);
    }

    @RoleCustomer
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> update(@PathVariable("id") Integer id, @RequestBody @Validated AddressRequestDTO request) {
        return new ResponseEntity<>(service.update(id, request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @RoleCustomer
    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> findAll(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(service.findAll(user.getId()), HttpStatus.OK);
    }

    @RoleAdmin
    @GetMapping("/all/{customerId}")
    public ResponseEntity<List<AddressResponseDTO>> findAll(@PathVariable("customerId") Integer customerId) {
        return new ResponseEntity<>(service.findAll(customerId), HttpStatus.OK);
    }

    @RoleCustomer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
