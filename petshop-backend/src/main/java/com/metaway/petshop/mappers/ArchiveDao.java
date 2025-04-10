package com.metaway.petshop.mappers;

import com.metaway.petshop.models.myBatis.ArchiveV2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArchiveDao {

    void save(ArchiveV2 archive);

    ArchiveV2 findById(@Param("id") Integer id);

    void delete(@Param("id") Integer id);

    void deleteArchive(@Param("id") Integer id, @Param("isUser") boolean isUser);

}
