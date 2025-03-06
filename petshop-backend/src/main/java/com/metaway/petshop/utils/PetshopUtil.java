package com.metaway.petshop.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PetshopUtil {

    public static final String ERROR_MESSAGE = "Um erro inesperado aconteceu. Entre em contato com o administrador do sistema!";

    public static <T, E> List<T> convert(Function<E, T> converter, List<E> list) {
        return list.stream().map(converter).collect(Collectors.toList());
    }

}
