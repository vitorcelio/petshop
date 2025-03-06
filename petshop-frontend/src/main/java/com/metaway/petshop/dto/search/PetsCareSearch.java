package com.metaway.petshop.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetsCareSearch {

    private Integer petId;
    private Integer customerId;
    private String status;
    private String createdAtStart;
    private String createdAtEnd;

}
