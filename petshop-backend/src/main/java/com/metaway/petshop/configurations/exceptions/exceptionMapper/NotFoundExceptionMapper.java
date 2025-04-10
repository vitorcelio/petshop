package com.metaway.petshop.configurations.exceptions.exceptionMapper;

import com.metaway.petshop.configurations.exceptions.NotFoundException;
import com.metaway.petshop.utils.PetshopUtil;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        return PetshopUtil.getResponse(Response.Status.NOT_FOUND, exception.getMessage());
    }
}
