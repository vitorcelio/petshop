package com.metaway.petshop.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.metaway.petshop.enums.PetCareStatus;
import com.metaway.petshop.models.PetCare;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetCareRequestDTO {

    @NotNull(message = "O animal é obrigatório.")
    private Integer petId;

    @NotBlank(message = "A descrição é obrigatória.")
    private String description;

    @NotNull(message = "O preço é obrigatório.")
    private BigDecimal price;

    @NotNull(message = "A data é obrigatória.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime date;

    @NotBlank(message = "O status é obrigatório.")
    private String status;

    public PetCare toPetCare() {
        return PetCare.builder()
                .petId(petId)
                .description(description)
                .price(price)
                .date(date)
                .status(PetCareStatus.PENDING)
                .build();
    }

}
