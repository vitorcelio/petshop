package com.metaway.petshop.models.myBatis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveV2 {

    private Integer id;
    private String base64;
    private String base64Mini;

    public ArchiveV2(String base64, String base64Mini) {
        this.base64 = base64;
        this.base64Mini = base64Mini;
    }
}
