package com.metaway.petshop.services.contact;

import com.metaway.petshop.dto.request.ContactRequestDTO;
import com.metaway.petshop.enums.ContactType;
import com.metaway.petshop.models.myBatis.ContactV2;

import java.time.LocalDateTime;
import java.util.List;

public interface ContactServiceV2 {

    ContactV2 save(ContactRequestDTO request);

    ContactV2 update(String uuid, ContactRequestDTO request);

    ContactV2 findByUuid(String uuid);

    List<ContactV2> findAll(Integer customerId,
                            String tag,
                            ContactType type,
                            LocalDateTime createdAtStart,
                            LocalDateTime createdAtEnd,
                            Integer page,
                            Integer quantity);

    void delete(String uuid);

}
