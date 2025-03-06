package com.metaway.petshop.controllers;

import com.metaway.petshop.configurations.security.RoleAdmin;
import com.metaway.petshop.configurations.security.RoleCustomer;
import com.metaway.petshop.configurations.swagger.AuthorizationSwagger;
import com.metaway.petshop.dto.request.*;
import com.metaway.petshop.dto.response.UserResponseDTO;
import com.metaway.petshop.models.User;
import com.metaway.petshop.services.user.UserService;
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
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponseDTO> save(@RequestBody @Validated UserRequestDTO request) {
        return new ResponseEntity<>(service.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @AuthorizationSwagger
    public ResponseEntity<UserResponseDTO> update(@AuthenticationPrincipal User user,
                                                  @RequestBody @Validated UserUpdateRequestDTO request) {
        return new ResponseEntity<>(service.update(user.getId(), request), HttpStatus.OK);
    }

    @RoleAdmin
    @PostMapping("/internal")
    @AuthorizationSwagger
    public ResponseEntity<UserResponseDTO> save(@RequestBody @Validated UserInternalRequestDTO request) {
        return new ResponseEntity<>(service.save(request), HttpStatus.CREATED);
    }

    @RoleAdmin
    @PutMapping("/internal/update/{id}")
    @AuthorizationSwagger
    public ResponseEntity<UserResponseDTO> update(@PathVariable("id") Integer id,
                                                  @RequestBody @Validated UserInternalUpdateRequestDTO request) {
        return new ResponseEntity<>(service.update(id, request), HttpStatus.OK);
    }

    @RoleAdmin
    @GetMapping("/{id}")
    @AuthorizationSwagger
    public ResponseEntity<UserResponseDTO> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping("/me")
    @AuthorizationSwagger
    public ResponseEntity<UserResponseDTO> findMe(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(service.findById(user.getId()), HttpStatus.OK);
    }

    @RoleAdmin
    @GetMapping("/cpf/{cpf}")
    @AuthorizationSwagger
    public ResponseEntity<UserResponseDTO> findByCpf(@PathVariable("cpf") String cpf) {
        return new ResponseEntity<>(service.findByCpf(cpf), HttpStatus.OK);
    }

    @RoleAdmin
    @PatchMapping("/change-password/{id}")
    @AuthorizationSwagger
    public ResponseEntity<Void> updatePassword(@PathVariable("id") Integer id,
                                               @RequestBody @Validated RecoverPasswordRequestDTO request) {
        service.updatePassword(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/change-password")
    @AuthorizationSwagger
    public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal User user,
                                               @RequestBody @Validated RecoverPasswordRequestDTO request) {
        service.updatePassword(user.getId(), request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RoleAdmin
    @GetMapping("/all/{roleId}")
    @AuthorizationSwagger
    public ResponseEntity<List<UserResponseDTO>> findAll(@PathVariable("roleId") Integer roleId,
                                                         @RequestParam(name = "name", required = false) String name,
                                                         @RequestParam(name = "cpf", required = false) String cpf,
                                                         @RequestParam(name = "createdAtStart", required = false) LocalDateTime createdAtStart,
                                                         @RequestParam(name = "createdAtEnd", required = false) LocalDateTime createdAtEnd
    ) {
        return new ResponseEntity<>(service.findAll(roleId, name, cpf, createdAtStart, createdAtEnd), HttpStatus.OK);
    }

    @RoleAdmin
    @DeleteMapping("/{id}")
    @AuthorizationSwagger
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
