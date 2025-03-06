package com.metaway.petshop.dto.request;

import com.metaway.petshop.dto.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInternalUpdateRequestDTO {

    private String cpf;
    private String name;
    private boolean enabled;
    private Integer roleId;

}
