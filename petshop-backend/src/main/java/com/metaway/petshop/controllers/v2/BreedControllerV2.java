package com.metaway.petshop.controllers.v2;

import com.metaway.petshop.configurations.security.Secured;
import com.metaway.petshop.dto.request.BreedRequestDTO;
import com.metaway.petshop.enums.RolesEnum;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Secured
@Path("/breeds")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface BreedControllerV2 {

    @Secured(roles = RolesEnum.ADMIN)
    @POST
    Response save(@Valid BreedRequestDTO request);

    @Secured(roles = RolesEnum.ADMIN)
    @PUT
    @Path("/{id}")
    Response update(@PathParam("id") Integer id, @Valid BreedRequestDTO request);

    @GET
    @Path("/{id}")
    Response findById(@PathParam("id") Integer id);

    @GET
    Response findAll();

    @Secured(roles = RolesEnum.ADMIN)
    @DELETE
    @Path("/{id}")
    Response delete(@PathParam("id") Integer id);

}
