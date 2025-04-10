package com.metaway.petshop.controllers.v2;

import com.metaway.petshop.configurations.security.Secured;
import com.metaway.petshop.dto.request.AddressRequestDTO;
import com.metaway.petshop.enums.RolesEnum;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Secured
@Path("/addresses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AddressControllerV2 {

    @Secured(roles = RolesEnum.CUSTOMER)
    @POST
    Response save(@Valid AddressRequestDTO request);

    @Secured(roles = RolesEnum.CUSTOMER)
    @PUT
    @Path("/{id}")
    Response update(@PathParam("id") Integer id, @Valid AddressRequestDTO request);

    @GET
    @Path("/{id}")
    Response findById(@PathParam("id") Integer id);

    @Secured(roles = RolesEnum.CUSTOMER)
    @GET
    Response findAll(@Context SecurityContext securityContext);

    @Secured(roles = RolesEnum.ADMIN)
    @GET
    @Path("/all/{customerId}")
    Response findAll(@PathParam("customerId") Integer customerId);

    @Secured(roles = RolesEnum.CUSTOMER)
    @DELETE
    @Path("/{id}")
    Response delete(@PathParam("id") Integer id);

}
