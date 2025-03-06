package com.metaway.petshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInternalRequestDTO {

    private String cpf;
    private String name;
    private String password;
    private boolean enabled;
    private Integer roleId;

}
