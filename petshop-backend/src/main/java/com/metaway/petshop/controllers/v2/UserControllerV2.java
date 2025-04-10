package com.metaway.petshop.controllers.v2;

import com.metaway.petshop.configurations.security.Secured;
import com.metaway.petshop.dto.request.*;
import com.metaway.petshop.enums.RolesEnum;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface UserControllerV2 {

    @POST
    Response save(@Valid UserRequestDTO request);

    @Secured
    @PUT
    @Path("/update")
    Response update(@Context SecurityContext context, @Valid UserUpdateRequestDTO request);

    @Secured(roles = RolesEnum.ADMIN)
    @POST
    @Path("/internal")
    Response save(@Valid UserInternalRequestDTO request);

    @Secured(roles = RolesEnum.ADMIN)
    @PUT
    @Path("/internal/update/{id}")
    Response update(@PathParam("id") Integer id, @Valid UserInternalUpdateRequestDTO request);

    @Secured(roles = RolesEnum.ADMIN)
    @GET
    @Path("/{id}")
    Response findById(@PathParam("id") Integer id);

    @Secured
    @GET
    @Path("/me")
    Response findMe(@Context SecurityContext context);

    @Secured(roles = RolesEnum.ADMIN)
    @GET
    @Path("/cpf/{cpf}")
    Response findByCpf(@PathParam("cpf") String cpf);

    @Secured(roles = RolesEnum.ADMIN)
    @PATCH
    @Path("/change-password/{id}")
    Response updatePassword(@PathParam("id") Integer id, @Valid RecoverPasswordRequestDTO request);

    @Secured
    @PATCH
    @Path("/change-password")
    Response updatePassword(@Context SecurityContext context, @Valid RecoverPasswordRequestDTO request);

    @Secured(roles = RolesEnum.ADMIN)
    @GET
    @Path("/all/{roleId}")
    Response findAll(@PathParam("roleId") Integer roleId,
                     @QueryParam("name") String name,
                     @QueryParam("cpf") String cpf,
                     @QueryParam("createdAtStart") String createdAtStartStr,
                     @QueryParam("createdAtEnd") String createdAtEndStr,
                     @QueryParam("page") Integer page,
                     @QueryParam("quantity") Integer quantity
    );

    @Secured(roles = RolesEnum.ADMIN)
    @DELETE
    @Path("/{id}")
    Response delete(@PathParam("id") Integer id);

    @GET
    @Path("/ids/{ids}")
    Response findByIds(@PathParam("ids") String ids);

}
