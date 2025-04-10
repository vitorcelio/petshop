package com.metaway.petshop.controllers.v2.impl;

import com.metaway.petshop.controllers.v2.ArchiveControllerV2;
import com.metaway.petshop.dto.request.ArchiveRequestDTO;
import com.metaway.petshop.models.myBatis.UserV2;
import com.metaway.petshop.services.archive.ArchiveService;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ArchiveControllerV2Impl implements ArchiveControllerV2 {

    @Autowired
    @Qualifier("archiveServiceV2")
    private ArchiveService service;

    @Override
    public Response saveArchiveCustomer(ArchiveRequestDTO request) {
        service.saveArchiveCustomer(request);
        return Response.noContent().build();
    }

    @Override
    public Response saveArchivePet(ArchiveRequestDTO request) {
        service.saveArchivePet(request);
        return Response.noContent().build();
    }

    @Override
    public Response deleteArchiveCustomer(SecurityContext context) {
        UserV2 user = (UserV2) context.getUserPrincipal();
        service.deleteArchiveCustomer(user.getId());
        return Response.noContent().build();
    }

    @Override
    public Response deleteArchivePet(Integer petId) {
        service.deleteArchivePet(petId);
        return Response.noContent().build();
    }

}
