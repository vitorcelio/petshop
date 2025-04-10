package com.metaway.petshop.services.pet;

import com.metaway.petshop.dto.request.PetRequestDTO;
import com.metaway.petshop.enums.Gender;
import com.metaway.petshop.models.myBatis.PetV2;

import java.util.List;

public interface PetServiceV2 {

    PetV2 save(PetRequestDTO request);

    PetV2 update(Integer id, PetRequestDTO request);

    PetV2 findById(Integer id);

    List<PetV2> findAll(Integer customerId, String name, Integer breedId, Gender gender);

    void delete(Integer id);

}
