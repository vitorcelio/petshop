package com.metaway.petshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetCareRequestDTO {

    private transient String uuid;
    private Integer petId;
    private String description;
    private BigDecimal price;
    private LocalDateTime date;
    private String status;

}
