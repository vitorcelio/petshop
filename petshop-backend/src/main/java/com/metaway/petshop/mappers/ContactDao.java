package com.metaway.petshop.mappers;

import com.metaway.petshop.models.myBatis.ContactV2;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ContactDao {

    void save(ContactV2 contact);

    void update(ContactV2 contact);

    ContactV2 findByUuid(String uuid);

    List<ContactV2> findAll(Map<String, Object> params);

    void delete(String uuid);

}
