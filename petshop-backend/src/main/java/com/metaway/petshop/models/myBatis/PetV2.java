package com.metaway.petshop.models.myBatis;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.metaway.petshop.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetV2 {

    private Integer id;
    private String name;
    private Integer customerId;
    private Integer breedId;
    private Integer archiveId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDate;
    private Gender gender;

    private UserV2 customer;
    private BreedV2 breed;
    private ArchiveV2 archive;

}
