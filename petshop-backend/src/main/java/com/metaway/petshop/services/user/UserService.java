package com.metaway.petshop.services.user;

import com.metaway.petshop.dto.request.*;
import com.metaway.petshop.dto.response.UserResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {

    UserResponseDTO save(UserRequestDTO request);

    UserResponseDTO update(Integer id, UserUpdateRequestDTO request);

    UserResponseDTO save(UserInternalRequestDTO request);

    UserResponseDTO update(Integer id, UserInternalUpdateRequestDTO request);

    UserResponseDTO findById(Integer id);

    UserResponseDTO findByCpf(String cpf);

    void updatePassword(Integer id, RecoverPasswordRequestDTO request);

    List<UserResponseDTO> findAll(Integer roleId, String name, String cpf, LocalDateTime createdAtStart, LocalDateTime createdAtEnd);

    void delete(Integer id);

}
