package com.metaway.petshop.mappers;

import com.metaway.petshop.models.myBatis.AddressV2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddressDao {

    void save(AddressV2 address);

    void update(AddressV2 address);

    List<AddressV2> findAll(@Param("customerId") Integer customerId);

    AddressV2 findById(@Param("id") Integer id);

    void delete(@Param("id") Integer id);

}
