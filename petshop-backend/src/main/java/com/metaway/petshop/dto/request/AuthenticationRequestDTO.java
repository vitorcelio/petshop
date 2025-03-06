package com.metaway.petshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDTO {

    @NotBlank(message = "O CPF é obrigatório.")
    @CPF(message = "O CPF está inválido.")
    private String cpf;

    @NotBlank(message = "A senha é obrigatória")
    private String password;

}
