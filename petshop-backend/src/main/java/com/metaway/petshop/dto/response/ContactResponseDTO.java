package com.metaway.petshop.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.metaway.petshop.enums.ContactType;
import com.metaway.petshop.models.Contact;
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
    private ContactType type;
    private String value;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime updatedAt;

    public ContactResponseDTO(Contact contact) {
        this.uuid = contact.getUuid();
        this.customer = new UserSummaryResponseDTO(contact.getCustomer());
        this.tag = contact.getTag();
        this.type = contact.getType();
        this.value = contact.getValue();
        this.message = contact.getMessage();
        this.createdAt = contact.getCreatedAt();
        this.updatedAt = contact.getUpdatedAt();
    }

}
