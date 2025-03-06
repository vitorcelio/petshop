package com.metaway.petshop.services.infos;

import com.metaway.petshop.dto.response.InfosResponseDTO;

public interface InfosService {

    InfosResponseDTO getInfosAdmin();

    InfosResponseDTO getInfosCustomer(Integer customerId);

}
