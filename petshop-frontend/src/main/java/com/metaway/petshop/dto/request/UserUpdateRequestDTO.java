package com.metaway.petshop.dto.request;

import com.metaway.petshop.dto.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDTO {

    private String cpf;
    private String name;

    public UserUpdateRequestDTO(UserResponseDTO user) {
        this.cpf = user.getCpf();
        this.name = user.getName();
    }

}
