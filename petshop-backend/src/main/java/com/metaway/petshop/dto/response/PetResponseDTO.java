package com.metaway.petshop.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.metaway.petshop.enums.Gender;
import com.metaway.petshop.models.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetResponseDTO {

    private Integer id;
    private String name;
    private UserSummaryResponseDTO customer;
    private BreedResponseDTO breed;
    private ArchiveResponseDTO archive;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    private Gender gender;

    public PetResponseDTO(Pet pet) {
        if (pet == null) {
            return;
        }

        this.id = pet.getId();
        this.name = pet.getName();
        this.customer = new UserSummaryResponseDTO(pet.getCustomer());
        this.breed = new BreedResponseDTO(pet.getBreed());
        this.birthDate = pet.getBirthDate();
        this.gender = pet.getGender();
        this.archive = new ArchiveResponseDTO(pet.getArchive());
    }

}
