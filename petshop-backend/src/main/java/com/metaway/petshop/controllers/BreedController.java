package com.metaway.petshop.controllers;

import com.metaway.petshop.configurations.security.RoleAdmin;
import com.metaway.petshop.configurations.swagger.AuthorizationSwagger;
import com.metaway.petshop.dto.request.BreedRequestDTO;
import com.metaway.petshop.dto.response.BreedResponseDTO;
import com.metaway.petshop.services.breed.BreedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/breeds")
@AuthorizationSwagger
public class BreedController {

    private final BreedService service;

    @RoleAdmin
    @PostMapping
    public ResponseEntity<BreedResponseDTO> save(@RequestBody @Validated BreedRequestDTO request) {
        return new ResponseEntity<>(service.save(request), HttpStatus.CREATED);
    }

    @RoleAdmin
    @PutMapping("/{id}")
    public ResponseEntity<BreedResponseDTO> update(@PathVariable("id") Integer id, @RequestBody @Validated BreedRequestDTO request) {
        return new ResponseEntity<>(service.update(id, request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BreedResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BreedResponseDTO>> findAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @RoleAdmin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
