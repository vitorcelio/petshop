package com.metaway.petshop.controllers.v2;

import com.metaway.petshop.configurations.security.Secured;
import com.metaway.petshop.configurations.swagger.AuthorizationSwagger;
import com.metaway.petshop.dto.request.PetRequestDTO;
import com.metaway.petshop.enums.Gender;
import com.metaway.petshop.enums.RolesEnum;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Secured
@Path("/pets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@AuthorizationSwagger
public interface PetControllerV2 {

    @Secured(roles = RolesEnum.CUSTOMER)
    @POST
    Response save(@Valid PetRequestDTO request);

    @Secured(roles = RolesEnum.CUSTOMER)
    @PUT
    @Path("/{id}")
    Response update(@PathParam("id") Integer id, @Valid PetRequestDTO request);

    @GET
    @Path("/{id}")
    Response findById(@PathParam("id") Integer id);

    @Secured(roles = RolesEnum.ADMIN)
    @GET
    @Path("/all")
    Response findAll(@QueryParam("customerId") Integer customerId, @QueryParam("name") String name, @QueryParam(
            "breedId") Integer breedId, @QueryParam("gender") Gender gender);

    @Secured(roles = RolesEnum.CUSTOMER)
    @GET
    Response findAll(@Context SecurityContext context, @QueryParam("name") String name, @QueryParam("breedId") Integer breedId,
                     @QueryParam("gender") Gender gender);

    @Secured(roles = RolesEnum.CUSTOMER)
    @DELETE
    @Path("/{id}")
    Response delete(@PathParam("id") Integer id);

}
