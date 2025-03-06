package com.metaway.petshop.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetSearch {

    private String name;
    private Integer breedId;
    private String gender;

}
