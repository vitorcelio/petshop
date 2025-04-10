package com.metaway.petshop.services.address;

import com.metaway.petshop.dto.request.AddressRequestDTO;
import com.metaway.petshop.models.myBatis.AddressV2;

import java.util.List;

public interface AddressServiceV2 {

    AddressV2 save(AddressRequestDTO request);

    AddressV2 update(Integer id, AddressRequestDTO request);

    AddressV2 findById(Integer id);

    List<AddressV2> findAll(Integer customerId);

    void delete(Integer id);

}
