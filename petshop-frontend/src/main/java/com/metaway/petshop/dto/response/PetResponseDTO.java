package com.metaway.petshop.dto.response;

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
    private LocalDate birthDate;
    private String gender;

}
