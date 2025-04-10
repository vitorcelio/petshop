package com.metaway.petshop.mappers;

import com.metaway.petshop.models.myBatis.RoleV2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleDao {

    RoleV2 findByUserId(@Param("userId") Integer userId);

    RoleV2 findById(@Param("id") Integer id);

}
