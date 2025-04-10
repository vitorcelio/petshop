package com.metaway.petshop.configurations.exceptions.exceptionMapper;

import com.metaway.petshop.configurations.exceptions.InternalServerException;
import com.metaway.petshop.utils.PetshopUtil;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class InternalServerExceptionMapper implements ExceptionMapper<InternalServerException> {

    @Override
    public Response toResponse(InternalServerException exception) {
        return PetshopUtil.getResponse(Response.Status.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
}
