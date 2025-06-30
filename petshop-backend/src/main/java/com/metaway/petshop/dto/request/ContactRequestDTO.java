package com.metaway.petshop.dto.request;

import com.metaway.petshop.enums.ContactType;
import com.metaway.petshop.models.Contact;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequestDTO {

    @NotNull(message = "O cliente é obrigatório.")
    private Integer customerId;

    @NotBlank(message = "A tag é obrigatória.")
    private String tag;

    @NotBlank(message = "O tipo de contato é obrigatório.")
    private String type;

    @NotBlank(message = "O valor é obrigatório.")
    private String value;

    @NotBlank(message = "A mensagem é obrigatória.")
    private String message;

    public Contact toContact() {
        return Contact.builder()
                .customerId(customerId)
                .tag(tag)
                .type(ContactType.valueOf(type))
                .value(value)
                .message(message)
                .build();
    }

}
