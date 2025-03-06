package com.metaway.petshop.services.petCare;

import com.metaway.petshop.dto.request.PetCareRequestDTO;
import com.metaway.petshop.dto.response.PetCareResponseDTO;
import com.metaway.petshop.enums.PetCareStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface PetCareService {

    PetCareResponseDTO save(PetCareRequestDTO request);

    PetCareResponseDTO update(String uuid, PetCareRequestDTO request);

    PetCareResponseDTO findByUuid(String uuid);

    List<PetCareResponseDTO> findAll(Integer customerId, Integer petId, String name, PetCareStatus status, LocalDateTime createAtStart,
                                     LocalDateTime createAtEnd);

    List<PetCareResponseDTO> findByPetId(Integer petId);

    void delete(String uuid);

}
