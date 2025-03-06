package com.metaway.petshop.services.pet;

import com.metaway.petshop.dto.request.PetRequestDTO;
import com.metaway.petshop.dto.response.PetResponseDTO;
import com.metaway.petshop.enums.Gender;

import java.util.List;

public interface PetService {

    PetResponseDTO save(PetRequestDTO request);

    PetResponseDTO update(Integer id, PetRequestDTO request);

    PetResponseDTO findById(Integer id);

    List<PetResponseDTO> findAll(Integer customerId, String name, Integer breedId, Gender gender);

    void delete(Integer id);

}
