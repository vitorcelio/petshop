package com.metaway.petshop.models.myBatis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleV2 implements GrantedAuthority {

    private Integer id;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

}
