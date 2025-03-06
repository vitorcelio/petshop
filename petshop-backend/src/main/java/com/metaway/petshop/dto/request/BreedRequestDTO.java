package com.metaway.petshop.dto.request;

import com.metaway.petshop.models.Breed;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreedRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotBlank(message = "A descrição é obrigatória.")
    private String description;

    public Breed toBreed() {
        return Breed.builder()
                .name(name)
                .description(description)
                .build();
    }

}
