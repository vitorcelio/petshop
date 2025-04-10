package com.metaway.petshop.mappers;

import com.metaway.petshop.models.myBatis.BreedV2;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BreedDao {

    void save(BreedV2 breed);

    @Insert("insert into breeds(name, description) values (#{name}, #{description})")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    void save2(BreedV2 breed);

    BreedV2 findById(@Param("id") Integer id);

    void update(BreedV2 breed);

    List<BreedV2> findAll();

    void delete(@Param("id") Integer id);

}
