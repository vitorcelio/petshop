package com.metaway.petshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponseDTO {

    private Integer id;
    private String street;
    private String district;
    private String complement;
    private String city;
    private String state;
    private String postalCode;
    private String tag;

}
