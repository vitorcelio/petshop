package com.metaway.petshop.repositories;

import com.metaway.petshop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    @Query(value = """
        select * from users u where u.role_id = :roleId
                and u.account_deleted = false
                and (:name is null or LOWER(u.name) like LOWER(CONCAT('%', :name, '%')))
                and (:cpf is null or LOWER(u.cpf) like LOWER(CONCAT('%', :cpf, '%')))
                and (CAST(:createdAtStart as TIMESTAMP) is null or u.created_at >= CAST(:createdAtStart as TIMESTAMP))
                and (CAST(:createdAtEnd as TIMESTAMP) is null or u.created_at <= CAST(:createdAtEnd as TIMESTAMP))
        """, nativeQuery = true)
    List<User> findByUsers(Integer roleId, String name, String cpf, LocalDateTime createdAtStart,
                           LocalDateTime createdAtEnd);

    @Query("select count(u) from User u where u.roleId = :roleId")
    Integer countUserByRoleId(Integer roleId);

}
