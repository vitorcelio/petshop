package com.metaway.petshop.models.myBatis;

import com.metaway.petshop.enums.ContactType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactV2 {

    private String uuid;
    private Integer customerId;
    private String tag;
    private ContactType type;
    private String value;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private UserV2 customer;

}
