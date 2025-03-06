package com.metaway.petshop.configurations.security;

import com.metaway.petshop.configurations.exceptions.ForbiddenException;
import com.metaway.petshop.configurations.exceptions.UnauthorizedException;
import com.metaway.petshop.enums.RolesEnum;
import com.metaway.petshop.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityVerify {

    private User userLogged() {
        try {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new UnauthorizedException("Usuário não autenticado!");
        }
    }

    public void verifyUser(Integer id) {
        if (!userLogged().getId().equals(id)) {
            throw new ForbiddenException("Você não tem permissão para executar essa função!");
        }
    }

    public void verifyUserOrAdmin(Integer id) {
        if (isAdmin()) {
            return;
        }

       verifyUser(id);
    }

    public boolean isAdmin() {
        return userLogged().getRoleId().equals(RolesEnum.ADMIN.getId());
    }

    public boolean isCustomer() {
        return userLogged().getRoleId().equals(RolesEnum.CUSTOMER.getId());
    }

    public Integer getLoggedUserId() {
        return userLogged().getId();
    }

    public String getLoggedCPF() {
        return userLogged().getCpf();
    }

}
