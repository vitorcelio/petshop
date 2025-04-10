package com.metaway.petshop.controllers.v2;

import com.metaway.petshop.configurations.security.RoleAdmin;
import com.metaway.petshop.configurations.security.RoleCustomer;
import com.metaway.petshop.configurations.security.Secured;
import com.metaway.petshop.dto.request.ContactRequestDTO;
import com.metaway.petshop.dto.response.ContactResponseDTO;
import com.metaway.petshop.enums.ContactType;
import com.metaway.petshop.enums.RolesEnum;
import com.metaway.petshop.models.User;
import com.metaway.petshop.services.contact.ContactService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/*@Secured
@Path("/contacts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)*/
public class ContactControllerV2 {

   /* private ContactService service;

    @Secured(roles = RolesEnum.CUSTOMER)
    @POST
    public Response save(@Valid ContactRequestDTO request) {
        return Response.status(Status.CREATED).entity(service.save(request)).build();
    }

    @Secured(roles = RolesEnum.CUSTOMER)
    @PUT
    @Path("/{uuid}")
    public Response update(@PathParam("uuid") String uuid, @Valid ContactRequestDTO request) {
        return Response.status(Status.OK).entity(service.update(uuid, request)).build();
    }

    @GET
    @Path("/{uuid}")
    public ResponseEntity<ContactResponseDTO> findByUuid(@PathVariable("uuid") String uuid) {
        return new ResponseEntity<>(service.findByUuid(uuid), HttpStatus.OK);
    }

    @RoleAdmin
    @GetMapping("/all")
    public ResponseEntity<List<ContactResponseDTO>> findAll(
            @QueryParam("customerId") Integer customerId,
            @QueryParam("tag") String tag,
            @QueryParam("type") ContactType type,
            @QueryParam("createdAtStart") LocalDateTime createdAtStart,
            @QueryParam("createdAtEnd") LocalDateTime createdAtEnd) {
        return new ResponseEntity<>(service.findAll(customerId, tag, type, createdAtStart, createdAtEnd),
                HttpStatus.OK);
    }

    @RoleCustomer
    @GetMapping
    public ResponseEntity<List<ContactResponseDTO>> findAll(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "type", required = false) ContactType type,
            @RequestParam(name = "createdAtStart", required = false) LocalDateTime createdAtStart,
            @RequestParam(name = "createdAtEnd", required = false) LocalDateTime createdAtEnd) {
        return new ResponseEntity<>(service.findAll(user.getId(), tag, type, createdAtStart, createdAtEnd),
                HttpStatus.OK);
    }

    @RoleCustomer
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable("uuid") String uuid) {
        service.delete(uuid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

}
