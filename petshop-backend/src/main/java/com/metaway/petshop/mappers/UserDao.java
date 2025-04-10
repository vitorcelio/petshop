package com.metaway.petshop.mappers;

import com.metaway.petshop.models.myBatis.UserV2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {

    void save(UserV2 user);

    void update(UserV2 user);

    UserV2 findById(@Param("id") Integer id);

    UserV2 findByCpf(@Param("cpf") String cpf);

    void updatePassword(@Param("id") Integer id, @Param("password") String password);

    List<UserV2> findAll(Map<String, Object> params);

    List<UserV2> findByIds(@Param("ids") List<Integer> ids);

    void delete(@Param("id") Integer id);

    boolean existCPF(@Param("cpf") String cpf);

}
