package com.metaway.petshop.dto.request;

import com.metaway.petshop.enums.RolesEnum;
import com.metaway.petshop.models.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "O CPF é obrigatório.")
    @CPF(message = "O CPF está inválido.")
    private String cpf;

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @Size(min = 5, message = "O tamanho mínimo da senha deve ser de 5 caracteres.")
    @NotBlank(message = "A senha é obrigatória")
    private String password;

    public User toUser() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return User.builder()
                .cpf(cpf)
                .name(name)
                .password(encoder.encode(password))
                .roleId(RolesEnum.CUSTOMER.getId())
                .build();
    }

}
