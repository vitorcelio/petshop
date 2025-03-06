package com.metaway.petshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDTO {

    private transient Integer id;
    private Integer customerId;
    private String street;
    private String district;
    private String complement;
    private String city;
    private String state;
    private String postalCode;
    private String tag;

}
