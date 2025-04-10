package com.metaway.petshop.models.myBatis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreedV2 {

    private Integer id;
    private String name;
    private String description;

    public BreedV2(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
