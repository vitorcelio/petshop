package com.metaway.petshop.enums;

public enum RolesEnum {

    ADMIN(1), CUSTOMER(2);

    private Integer id;

    RolesEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
