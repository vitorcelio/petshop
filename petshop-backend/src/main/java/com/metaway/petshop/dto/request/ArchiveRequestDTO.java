package com.metaway.petshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveRequestDTO {

    @NotNull(message = "Atribua a imagem a um Cliente ou Animal de estimação")
    private Integer objectId;

    @NotBlank(message = "A imagem é obrigatória.")
    private String base64;

    private String base64Mini;

}
