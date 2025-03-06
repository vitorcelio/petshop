package com.metaway.petshop.dto.request;

import com.metaway.petshop.models.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDTO {

    @NotNull(message = "O cliente é obrigatório.")
    private Integer customerId;

    @NotBlank(message = "A rua é obrigatória.")
    private String street;

    @NotBlank(message = "O bairro é obrigatório.")
    private String district;

    @NotBlank(message = "O complemento é obrigatório.")
    private String complement;

    @NotBlank(message = "A cidade é obrigatória.")
    private String city;

    @NotBlank(message = "O estado é obrigatório.")
    private String state;

    @NotBlank(message = "O CEP é obrigatório.")
    private String postalCode;

    @NotBlank(message = "A tag é obrigatória.")
    private String tag;

    public Address toAddress() {
        return Address.builder()
                .customerId(customerId)
                .street(street)
                .district(district)
                .complement(complement)
                .city(city)
                .state(state)
                .postalCode(postalCode)
                .tag(tag)
                .build();
    }

}
