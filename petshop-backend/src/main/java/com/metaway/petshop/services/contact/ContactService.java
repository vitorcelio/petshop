package com.metaway.petshop.services.contact;

import com.metaway.petshop.dto.request.ContactRequestDTO;
import com.metaway.petshop.dto.response.ContactResponseDTO;
import com.metaway.petshop.enums.ContactType;

import java.time.LocalDateTime;
import java.util.List;

public interface ContactService {

    ContactResponseDTO save(ContactRequestDTO request);

    ContactResponseDTO update(String uuid, ContactRequestDTO request);

    ContactResponseDTO findByUuid(String uuid);

    List<ContactResponseDTO> findAll(Integer customerId, String tag, ContactType type,
                                     LocalDateTime createdAtStart, LocalDateTime createdAtEnd);

    void delete(String uuid);

}
