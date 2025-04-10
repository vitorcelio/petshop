package com.metaway.petshop.enums;

import lombok.Getter;

@Getter
public enum RolesEnum {

    ADMIN(1), CUSTOMER(2);

    private final Integer id;

    RolesEnum(Integer id) {
        this.id = id;
    }

}
