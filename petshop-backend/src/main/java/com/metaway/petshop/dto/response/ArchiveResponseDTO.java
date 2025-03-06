package com.metaway.petshop.dto.response;

import com.metaway.petshop.models.Archive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArchiveResponseDTO {

    private Integer id;
    private String base64;
    private String base64Mini;

    public ArchiveResponseDTO(Archive archive) {
        if (archive == null) {
            return;
        }

        this.id = archive.getId();
        this.base64 = archive.getBase64();

        if (archive.getBase64Mini() != null) {
            this.base64Mini = archive.getBase64Mini();
        } else {
            this.base64Mini = archive.getBase64();
        }
    }

}
