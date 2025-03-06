package com.metaway.petshop.controllers;

import com.metaway.petshop.configurations.security.RoleAdmin;
import com.metaway.petshop.configurations.security.RoleCustomer;
import com.metaway.petshop.configurations.swagger.AuthorizationSwagger;
import com.metaway.petshop.dto.request.PetRequestDTO;
import com.metaway.petshop.dto.response.PetResponseDTO;
import com.metaway.petshop.enums.Gender;
import com.metaway.petshop.models.User;
import com.metaway.petshop.services.pet.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pets")
@AuthorizationSwagger
public class PetController {

    private final PetService service;

    @RoleCustomer
    @PostMapping
    public ResponseEntity<PetResponseDTO> save(@RequestBody @Validated PetRequestDTO request) {
        return new ResponseEntity<>(service.save(request), HttpStatus.CREATED);
    }

    @RoleCustomer
    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDTO> update(@PathVariable("id") Integer id, @RequestBody @Validated PetRequestDTO request) {
        return new ResponseEntity<>(service.update(id, request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @RoleAdmin
    @GetMapping("/all")
    public ResponseEntity<List<PetResponseDTO>> findAll(
            @RequestParam(name = "customerId", required = false) Integer customerId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "breedId", required = false) Integer breedId,
            @RequestParam(name = "gender", required = false) Gender gender) {
        return new ResponseEntity<>(service.findAll(customerId, name, breedId, gender), HttpStatus.OK);
    }

    @RoleCustomer
    @GetMapping
    public ResponseEntity<List<PetResponseDTO>> findAll(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "breedId", required = false) Integer breedId,
            @RequestParam(name = "gender", required = false) Gender gender) {
        return new ResponseEntity<>(service.findAll(user.getId(), name, breedId, gender), HttpStatus.OK);
    }

    @RoleCustomer
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
