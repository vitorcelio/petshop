package com.metaway.petshop.controllers;

import com.metaway.petshop.configurations.security.RoleAdmin;
import com.metaway.petshop.configurations.security.RoleCustomer;
import com.metaway.petshop.configurations.swagger.AuthorizationSwagger;
import com.metaway.petshop.dto.request.ContactRequestDTO;
import com.metaway.petshop.dto.response.ContactResponseDTO;
import com.metaway.petshop.enums.ContactType;
import com.metaway.petshop.models.User;
import com.metaway.petshop.services.contact.ContactService;
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
@RequestMapping("/api/v1/contacts")
@AuthorizationSwagger
public class ContactController {

    private final ContactService service;

    @RoleCustomer
    @PostMapping
    public ResponseEntity<ContactResponseDTO> save(@RequestBody @Validated ContactRequestDTO request) {
        return new ResponseEntity<>(service.save(request), HttpStatus.CREATED);
    }

    @RoleCustomer
    @PutMapping("/{uuid}")
    public ResponseEntity<ContactResponseDTO> update(@PathVariable("uuid") String uuid, @RequestBody @Validated ContactRequestDTO request) {
        return new ResponseEntity<>(service.update(uuid, request), HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ContactResponseDTO> findByUuid(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(service.findByUuid(uuid), HttpStatus.OK);
    }

    @RoleAdmin
    @GetMapping("/all")
    public ResponseEntity<List<ContactResponseDTO>> findAll(
            @RequestParam(name = "customerId", required = false) Integer customerId,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "type", required = false) ContactType type,
            @RequestParam(name = "createdAtStart", required = false) LocalDateTime createdAtStart,
            @RequestParam(name = "createdAtEnd", required = false) LocalDateTime createdAtEnd) {
        return new ResponseEntity<>(service.findAll(customerId, tag, type, createdAtStart, createdAtEnd), HttpStatus.OK);
    }

    @RoleCustomer
    @GetMapping
    public ResponseEntity<List<ContactResponseDTO>> findAll(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "type", required = false) ContactType type,
            @RequestParam(name = "createdAtStart", required = false) LocalDateTime createdAtStart,
            @RequestParam(name = "createdAtEnd", required = false) LocalDateTime createdAtEnd) {
        return new ResponseEntity<>(service.findAll(user.getId(), tag, type, createdAtStart, createdAtEnd), HttpStatus.OK);
    }

    @RoleCustomer
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable("uuid") String uuid) {
        service.delete(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
