package com.metaway.petshop.configurations.exceptions.exceptionMapper;

import com.metaway.petshop.configurations.exceptions.ForbiddenException;
import com.metaway.petshop.utils.PetshopUtil;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

    @Override
    public Response toResponse(ForbiddenException exception) {
        return PetshopUtil.getResponse(Response.Status.FORBIDDEN, exception.getMessage());
    }
}
