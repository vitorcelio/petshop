package com.metaway.petshop.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSearch {

    private String name;
    private String cpf;
    private String createdAtStart;
    private String createdAtEnd;

}
