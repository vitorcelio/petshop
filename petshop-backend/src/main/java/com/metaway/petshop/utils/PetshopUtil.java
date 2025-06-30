package com.metaway.petshop.utils;

import com.metaway.petshop.configurations.exceptions.ExceptionDetails;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PetshopUtil {

    public static final String CONSUMER_EMAIL = "petshop@email";

    public static final String ERROR_MESSAGE = "Um erro inesperado aconteceu. Entre em contato com o administrador do sistema!";

    public static <T, E> List<T> convert(Function<E, T> converter, List<E> list) {
        return list.stream().map(converter).collect(Collectors.toList());
    }

    public static Response getResponse(Response.Status status, String message) {
        String msg = message.split(":").length > 1 ? message.split(": ")[1] : message;
        ExceptionDetails exceptionDetails = new ExceptionDetails(status.getReasonPhrase(), msg);
        return Response.status(status).entity(exceptionDetails).build();
    }

}
