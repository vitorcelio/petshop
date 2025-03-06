package com.metaway.petshop.services.breed;

import com.metaway.petshop.dto.request.BreedRequestDTO;
import com.metaway.petshop.dto.response.BreedResponseDTO;

import java.util.List;

public interface BreedService {

    BreedResponseDTO save(BreedRequestDTO request);

    BreedResponseDTO update(Integer id, BreedRequestDTO request);

    BreedResponseDTO findById(Integer id);

    List<BreedResponseDTO> findAll();

    void delete(Integer id);

}
