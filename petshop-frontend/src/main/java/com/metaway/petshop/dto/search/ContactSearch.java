package com.metaway.petshop.dto.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactSearch {

    private Integer customerId;
    private String tag;
    private String type;
    private String createdAtStart;
    private String createdAtEnd;

}
