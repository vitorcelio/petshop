package com.metaway.petshop.models.myBatis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressV2 {

    private Integer id;
    private Integer customerId;
    private String street;
    private String district;
    private String complement;
    private String city;
    private String state;
    private String postalCode;
    private String tag;

}
