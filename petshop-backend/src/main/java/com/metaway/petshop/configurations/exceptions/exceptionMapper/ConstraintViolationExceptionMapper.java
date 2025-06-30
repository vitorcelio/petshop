package com.metaway.petshop.configurations.exceptions.exceptionMapper;

import com.metaway.petshop.utils.PetshopUtil;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return PetshopUtil.getResponse(Response.Status.BAD_REQUEST, exception.getMessage());
    }
}
