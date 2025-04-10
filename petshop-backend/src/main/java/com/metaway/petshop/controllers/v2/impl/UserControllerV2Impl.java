package com.metaway.petshop.controllers.v2.impl;

import com.metaway.petshop.controllers.v2.UserControllerV2;
import com.metaway.petshop.dto.request.*;
import com.metaway.petshop.models.myBatis.UserV2;
import com.metaway.petshop.services.user.UserServiceV2;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class UserControllerV2Impl implements UserControllerV2 {

    private final UserServiceV2 service;

    @Override
    public Response save(UserRequestDTO request) {
        return Response.status(Status.CREATED).entity(service.save(request)).build();
    }

    @Override
    public Response update(SecurityContext context, UserUpdateRequestDTO request) {
        UserV2 user = (UserV2) context.getUserPrincipal();
        return Response.status(Status.OK).entity(service.update(user.getId(), request)).build();
    }

    @Override
    public Response save(UserInternalRequestDTO request) {
        return Response.status(Status.CREATED).entity(service.save(request)).build();
    }

    @Override
    public Response update(Integer id, UserInternalUpdateRequestDTO request) {
        return Response.status(Status.OK).entity(service.update(id, request)).build();
    }

    @Override
    public Response findById(Integer id) {
        return Response.status(Status.OK).entity(service.findById(id)).build();
    }

    @Override
    public Response findMe(SecurityContext context) {
        UserV2 user = (UserV2) context.getUserPrincipal();
        return Response.status(Status.OK).entity(service.findById(user.getId())).build();
    }

    @Override
    public Response findByCpf(String cpf) {
        return Response.status(Status.OK).entity(service.findByCpf(cpf)).build();
    }

    @Override
    public Response updatePassword(Integer id, RecoverPasswordRequestDTO request) {
        service.updatePassword(id, request);
        return Response.status(Status.NO_CONTENT).build();
    }

    @Override
    public Response updatePassword(SecurityContext context, RecoverPasswordRequestDTO request) {
        UserV2 user = (UserV2) context.getUserPrincipal();
        service.updatePassword(user.getId(), request);
        return Response.status(Status.NO_CONTENT).build();
    }

    @Override
    public Response findAll(Integer roleId, String name, String cpf, String createdAtStartStr, String createdAtEndStr
            , Integer page, Integer quantity) {
        LocalDateTime createdAtStart = createdAtStartStr != null ? LocalDateTime.parse(createdAtStartStr) : null;
        LocalDateTime createdAtEnd = createdAtEndStr != null ? LocalDateTime.parse(createdAtEndStr) : null;

        return Response.status(Status.OK).entity(service.findAll(roleId, name, cpf, createdAtStart, createdAtEnd, page, quantity)).build();
    }

    @Override
    public Response delete(Integer id) {
        service.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @Override
    public Response findByIds(String ids) {
        return Response.status(Status.OK).entity(service.findByIds(ids)).build();
    }

}
