package com.metaway.petshop.controllers.v2.impl;

import com.metaway.petshop.controllers.v2.AddressControllerV2;
import com.metaway.petshop.dto.request.AddressRequestDTO;
import com.metaway.petshop.models.myBatis.UserV2;
import com.metaway.petshop.services.address.AddressServiceV2;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AddressControllerV2Impl implements AddressControllerV2 {

    private final AddressServiceV2 service;

    @Override
    public Response save(AddressRequestDTO request) {
        return Response.status(Status.CREATED).entity(service.save(request)).build();
    }

    @Override
    public Response update(Integer id, AddressRequestDTO request) {
        return Response.status(Status.OK).entity(service.update(id, request)).build();
    }

    @Override
    public Response findById(Integer id) {
        return Response.status(Status.OK).entity(service.findById(id)).build();
    }

    @Override
    public Response findAll(SecurityContext securityContext) {
        UserV2 user = (UserV2) securityContext.getUserPrincipal();
        return Response.status(Status.OK).entity(service.findAll(user.getId())).build();
    }

    @Override
    public Response findAll(Integer customerId) {
        return Response.status(Status.OK).entity(service.findAll(customerId)).build();
    }

    @Override
    public Response delete(Integer id) {
        service.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

}
