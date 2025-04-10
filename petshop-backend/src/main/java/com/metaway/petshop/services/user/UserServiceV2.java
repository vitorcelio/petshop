package com.metaway.petshop.services.user;

import com.metaway.petshop.dto.request.*;
import com.metaway.petshop.models.myBatis.UserV2;
import jakarta.ws.rs.QueryParam;

import java.time.LocalDateTime;
import java.util.List;

public interface UserServiceV2 {

    UserV2 save(UserRequestDTO request);

    UserV2 update(Integer id, UserUpdateRequestDTO request);

    UserV2 save(UserInternalRequestDTO request);

    UserV2 update(Integer id, UserInternalUpdateRequestDTO request);

    UserV2 findById(Integer id);

    UserV2 findByCpf(String cpf);

    void updatePassword(Integer id, RecoverPasswordRequestDTO request);

    List<UserV2> findAll(Integer roleId, String name, String cpf, LocalDateTime createdAtStart, LocalDateTime createdAtEnd, Integer page, Integer quantity);

    List<UserV2> findByIds(String ids);

    void delete(Integer id);

}
