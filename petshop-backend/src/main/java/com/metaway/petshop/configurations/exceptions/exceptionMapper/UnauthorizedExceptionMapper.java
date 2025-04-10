package com.metaway.petshop.configurations.exceptions.exceptionMapper;

import com.metaway.petshop.configurations.exceptions.UnauthorizedException;
import com.metaway.petshop.utils.PetshopUtil;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {

    @Override
    public Response toResponse(UnauthorizedException exception) {
        return PetshopUtil.getResponse(Response.Status.UNAUTHORIZED, exception.getMessage());
    }
}
