package com.metaway.petshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequestDTO {

    private transient String uuid;
    private Integer customerId;
    private String tag;
    private String type;
    private String value;
    private String message;

}
