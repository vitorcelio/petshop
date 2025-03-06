package com.metaway.petshop.backing;

import com.metaway.petshop.enums.RolesEnum;
import com.metaway.petshop.util.PetShopUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean
public class SessionBacking {

    public boolean isAdmin() {
        Integer roleId = (Integer) PetShopUtil.getObjectSession(PetShopUtil.ROLE_ID_SESSION);
        return RolesEnum.ADMIN.getId().equals(roleId);
    }

    public boolean isCustomer() {
        Integer roleId = (Integer) PetShopUtil.getObjectSession(PetShopUtil.ROLE_ID_SESSION);
        return RolesEnum.CUSTOMER.getId().equals(roleId);
    }


}
