package com.metaway.petshop.services.breed;

import com.metaway.petshop.dto.request.BreedRequestDTO;
import com.metaway.petshop.models.myBatis.BreedV2;

import java.util.List;

public interface BreedServiceV2 {

    BreedV2 save(BreedRequestDTO request);

    BreedV2 update(Integer id, BreedRequestDTO request);

    BreedV2 findById(Integer id);

    List<BreedV2> findAll();

    void delete(Integer id);

}
