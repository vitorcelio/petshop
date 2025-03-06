package com.metaway.petshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactResponseDTO {

    private String uuid;
    private UserSummaryResponseDTO customer;
    private String tag;
    private String type;
    private String value;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
