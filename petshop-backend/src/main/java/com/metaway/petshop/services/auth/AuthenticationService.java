package com.metaway.petshop.services.auth;

import com.metaway.petshop.dto.request.AuthenticationRequestDTO;
import com.metaway.petshop.dto.response.AuthenticationResponseDTO;

public interface AuthenticationService {

    AuthenticationResponseDTO login(AuthenticationRequestDTO request);

}
