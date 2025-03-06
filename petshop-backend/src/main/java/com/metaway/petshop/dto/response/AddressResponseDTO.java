package com.metaway.petshop.dto.response;

import com.metaway.petshop.models.Address;
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

    public AddressResponseDTO(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.district = address.getDistrict();
        this.complement = address.getComplement();
        this.city = address.getCity();
        this.state = address.getState();
        this.postalCode = address.getPostalCode();
        this.tag = address.getTag();
    }

}
