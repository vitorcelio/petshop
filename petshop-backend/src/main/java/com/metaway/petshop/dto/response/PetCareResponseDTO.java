package com.metaway.petshop.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.metaway.petshop.enums.PetCareStatus;
import com.metaway.petshop.models.PetCare;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetCareResponseDTO {

    private String uuid;
    private PetResponseDTO pet;
    private String description;
    private BigDecimal price;
    private PetCareStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime updatedAt;

    public PetCareResponseDTO(PetCare petCare) {
        this.uuid = petCare.getUuid();
        this.pet = new PetResponseDTO(petCare.getPet());
        this.description = petCare.getDescription();
        this.price = petCare.getPrice();
        this.status = petCare.getStatus();
        this.date = petCare.getDate();
        this.createdAt = petCare.getCreatedAt();
        this.updatedAt = petCare.getUpdatedAt();
    }

}
