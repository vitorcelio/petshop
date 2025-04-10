package com.metaway.petshop.mappers;

import com.metaway.petshop.models.myBatis.PetV2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PetDao {

    void save(PetV2 pet);

    void update(PetV2 pet);

    PetV2 findById(@Param("id") Integer id);

    List<PetV2> findAll(Map<String, Object> param);

    void delete(@Param("id") Integer id);

}
