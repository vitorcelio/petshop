package com.metaway.petshop.controllers.v2.impl;

import com.metaway.petshop.controllers.v2.BreedControllerV2;
import com.metaway.petshop.dto.request.BreedRequestDTO;
import com.metaway.petshop.services.breed.BreedServiceV2;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BreedControllerV2Impl implements BreedControllerV2 {

    private final BreedServiceV2 service;

    @Override
    public Response save(BreedRequestDTO request) {
        return Response.status(Status.CREATED).entity(service.save(request)).build();
    }

    @Override
    public Response update(Integer id, BreedRequestDTO request) {
        return Response.status(Status.OK).entity(service.update(id, request)).build();
    }

    @Override
    public Response findById(Integer id) {
        return Response.status(Status.OK).entity(service.findById(id)).build();
    }

    @Override
    public Response findAll() {
        return Response.status(Status.OK).entity(service.findAll()).build();
    }

    @Override
    public Response delete(Integer id) {
        service.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

}
