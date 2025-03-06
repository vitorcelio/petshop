package com.metaway.petshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetRequestDTO {

    private transient Integer id;
    private String name;
    private Integer customerId;
    private Integer breedId;
    private LocalDate birthDate;
    private String gender;

}
