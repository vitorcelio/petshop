package com.metaway.petshop.controllers;

import com.metaway.petshop.configurations.security.RoleCustomer;
import com.metaway.petshop.configurations.swagger.AuthorizationSwagger;
import com.metaway.petshop.dto.request.ArchiveRequestDTO;
import com.metaway.petshop.models.User;
import com.metaway.petshop.services.archive.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/archives")
@AuthorizationSwagger
public class ArchiveController {

    @Autowired
    @Qualifier("archiveServiceImpl")
    private ArchiveService service;

    @PostMapping("/customer")
    public ResponseEntity<Void> saveArchiveCustomer(@RequestBody @Validated ArchiveRequestDTO request) {
        service.saveArchiveCustomer(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RoleCustomer
    @PostMapping("/pet")
    public ResponseEntity<Void> saveArchivePet(@RequestBody @Validated ArchiveRequestDTO request) {
        service.saveArchivePet(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/customer")
    public ResponseEntity<Void> deleteArchiveCustomer(@AuthenticationPrincipal User user) {
        service.deleteArchiveCustomer(user.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RoleCustomer
    @DeleteMapping("/pet/{petId}")
    public ResponseEntity<Void> deleteArchivePet(@PathVariable Integer petId) {
        service.deleteArchivePet(petId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
