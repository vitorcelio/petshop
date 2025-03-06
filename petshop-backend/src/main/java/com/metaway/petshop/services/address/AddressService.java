package com.metaway.petshop.services.address;

import com.metaway.petshop.dto.request.AddressRequestDTO;
import com.metaway.petshop.dto.response.AddressResponseDTO;

import java.util.List;

public interface AddressService {

    AddressResponseDTO save(AddressRequestDTO request);

    AddressResponseDTO update(Integer id, AddressRequestDTO request);

    AddressResponseDTO findById(Integer id);

    List<AddressResponseDTO> findAll(Integer customerId);

    void delete(Integer id);

}
