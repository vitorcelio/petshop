package com.metaway.petshop.configurations.exceptions.exceptionMapper;

import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.utils.PetshopUtil;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException exception) {
        return PetshopUtil.getResponse(Response.Status.BAD_REQUEST, exception.getMessage());
    }
}
