package com.metaway.petshop.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.metaway.petshop.enums.Gender;
import com.metaway.petshop.models.Pet;
import com.metaway.petshop.models.myBatis.PetV2;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotNull(message = "O cliente é obrigatório.")
    private Integer customerId;

    @NotNull(message = "A raça é obrigatória.")
    private Integer breedId;

    @NotNull(message = "A data de aniversário é obrigatória.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @NotBlank(message = "O gênero é obrigatório.")
    private String gender;

    public Pet toPet() {
        return Pet.builder()
                .name(name)
                .customerId(customerId)
                .breedId(breedId)
                .birthDate(birthDate)
                .gender(Gender.valueOf(gender))
                .build();
    }

    public PetV2 toPetV2(Integer id) {
        return PetV2.builder()
                .id(id)
                .name(name)
                .customerId(customerId)
                .breedId(breedId)
                .birthDate(birthDate)
                .gender(Gender.valueOf(gender))
                .build();
    }

}
