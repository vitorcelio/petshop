package com.metaway.petshop.controllers.v2;

import com.metaway.petshop.configurations.security.Secured;
import com.metaway.petshop.dto.request.ArchiveRequestDTO;
import com.metaway.petshop.enums.RolesEnum;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Secured
@Path("/archives")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ArchiveControllerV2 {

    @POST
    @Path("/customer")
    Response saveArchiveCustomer(@Valid ArchiveRequestDTO request);

    @Secured(roles = RolesEnum.CUSTOMER)
    @POST
    Response saveArchivePet(@Valid ArchiveRequestDTO request);

    @DELETE
    @Path("/customer")
    Response deleteArchiveCustomer(@Context SecurityContext context);

    @Secured(roles = RolesEnum.CUSTOMER)
    @DELETE
    @Path("/pet/{petId}")
    Response deleteArchivePet(@PathParam("petId") Integer petId);

}
