package com.metaway.petshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecoverPasswordRequestDTO {

    @NotBlank(message = "A senha antiga é obrigatória.")
    private String oldPassword;

    @NotBlank(message = "A senha nova é obrigatória.")
    private String newPassword;

}
