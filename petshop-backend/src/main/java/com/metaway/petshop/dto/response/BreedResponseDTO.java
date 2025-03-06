package com.metaway.petshop.dto.response;

import com.metaway.petshop.models.Breed;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BreedResponseDTO {

    private Integer id;
    private String name;
    private String description;

    public BreedResponseDTO(Breed breed) {
        if (breed == null) {
            return;
        }

        this.id = breed.getId();
        this.name = breed.getName();
        this.description = breed.getDescription();
    }

}
