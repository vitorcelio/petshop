package com.metaway.petshop.repositories;

import com.metaway.petshop.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("""
        select u.role from User u where u.id = :userId
        """)
    Role findByUserId(Integer userId);

}
