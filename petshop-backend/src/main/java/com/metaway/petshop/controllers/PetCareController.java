package com.metaway.petshop.controllers;

import com.metaway.petshop.configurations.security.RoleAdmin;
import com.metaway.petshop.configurations.security.RoleCustomer;
import com.metaway.petshop.configurations.swagger.AuthorizationSwagger;
import com.metaway.petshop.dto.request.PetCareRequestDTO;
import com.metaway.petshop.dto.response.PetCareResponseDTO;
import com.metaway.petshop.enums.PetCareStatus;
import com.metaway.petshop.models.User;
import com.metaway.petshop.services.petCare.PetCareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/petsCare")
@AuthorizationSwagger
public class PetCareController {

    private final PetCareService service;

    @RoleAdmin
    @PostMapping
    public ResponseEntity<PetCareResponseDTO> save(@RequestBody @Validated PetCareRequestDTO request) {
        return new ResponseEntity<>(service.save(request), HttpStatus.CREATED);
    }

    @RoleAdmin
    @PutMapping("/{uuid}")
    public ResponseEntity<PetCareResponseDTO> update(@PathVariable("uuid") String uuid, @RequestBody @Validated PetCareRequestDTO request) {
        return new ResponseEntity<>(service.update(uuid, request), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PetCareResponseDTO> findByUuid(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(service.findByUuid(uuid), HttpStatus.OK);
    }

    @RoleAdmin
    @GetMapping("/all")
    public ResponseEntity<List<PetCareResponseDTO>> findAll(
            @RequestParam(name = "customerId", required = false) Integer customerId,
            @RequestParam(name = "petId", required = false) Integer petId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "status", required = false) PetCareStatus status,
            @RequestParam(name = "createdAtStart", required = false) LocalDateTime createdAtStart,
            @RequestParam(name = "createdAtEnd", required = false) LocalDateTime createdAtEnd
            ) {
        return new ResponseEntity<>(service.findAll(customerId, petId, name, status, createdAtStart, createdAtEnd), HttpStatus.OK);
    }

    @RoleCustomer
    @GetMapping
    public ResponseEntity<List<PetCareResponseDTO>> findAll(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "petId", required = false) Integer petId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "status", required = false) PetCareStatus status,
            @RequestParam(name = "createdAtStart", required = false) LocalDateTime createdAtStart,
            @RequestParam(name = "createdAtEnd", required = false) LocalDateTime createdAtEnd
    ) {
        return new ResponseEntity<>(service.findAll(user.getId(), petId, name, status, createdAtStart, createdAtEnd), HttpStatus.OK);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<PetCareResponseDTO>> findByPetId(@PathVariable("petId") Integer petId) {
        return new ResponseEntity<>(service.findByPetId(petId), HttpStatus.OK);
    }

    @RoleAdmin
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable("uuid") String uuid) {
        service.delete(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
