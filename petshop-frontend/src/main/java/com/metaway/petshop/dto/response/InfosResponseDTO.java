package com.metaway.petshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfosResponseDTO {

    private Integer petNumber;
    private Integer petCareNumber;
    private Integer petCareNumberPending;
    private Integer petCareNumberCanceled;
    private Integer customerNumber;
    private Integer contactsNumber;

}
