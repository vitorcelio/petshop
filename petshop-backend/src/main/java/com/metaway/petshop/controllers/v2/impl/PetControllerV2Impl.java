package com.metaway.petshop.controllers.v2.impl;

import com.metaway.petshop.controllers.v2.PetControllerV2;
import com.metaway.petshop.dto.request.PetRequestDTO;
import com.metaway.petshop.enums.Gender;
import com.metaway.petshop.models.myBatis.UserV2;
import com.metaway.petshop.services.pet.PetServiceV2;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PetControllerV2Impl implements PetControllerV2 {

    private final PetServiceV2 service;

    @Override
    public Response save(PetRequestDTO request) {
        return Response.status(Status.CREATED).entity(service.save(request)).build();
    }

    @Override
    public Response update(Integer id, PetRequestDTO request) {
        return Response.status(Status.OK).entity(service.update(id, request)).build();
    }

    @Override
    public Response findById(Integer id) {
        return Response.status(Status.OK).entity(service.findById(id)).build();
    }

    @Override
    public Response findAll(Integer customerId, String name, Integer breedId, Gender gender) {
        return Response.status(Status.OK).entity(service.findAll(customerId, name, breedId, gender)).build();
    }

    @Override
    public Response findAll(SecurityContext context, String name, Integer breedId, Gender gender) {
        UserV2 user = (UserV2) context.getUserPrincipal();
        return Response.status(Status.OK).entity(service.findAll(user.getId(), name, breedId,
                gender)).build();
    }

    @Override
    public Response delete(Integer id) {
        service.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

}
