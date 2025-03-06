package com.metaway.petshop.repositories;

import com.metaway.petshop.enums.PetCareStatus;
import com.metaway.petshop.models.PetCare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PetCareRepository extends JpaRepository<PetCare, String> {

    List<PetCare> findByPetId(Integer petId);

    @Query(value = """
            select pc.* from pet_care pc, pets p where 1=1
            and pc.pet_id = p.id
            and (:customerId is null or p.customer_id = :customerId)
            and (:petId is null or pc.pet_id = :petId)
            and (:name is null or UPPER(p.name) like UPPER(CONCAT('%', :name, '%')))
            and (CAST(:status as character varying) is null or pc.status = CAST(:status as character varying))
            and (CAST(:createdAtStart as timestamp) is null or pc.created_at >= CAST(:createdAtStart as timestamp))
            and (CAST(:createdAtEnd as timestamp) is null or pc.created_at <= CAST(:createdAtEnd as timestamp))
            """, nativeQuery = true)
    List<PetCare> findAll(Integer customerId, Integer petId, String name, String status, LocalDateTime createdAtStart, LocalDateTime createdAtEnd);

    @Transactional
    @Modifying
    @Query("delete from PetCare p where p.petId = :petId")
    void deleteByPetId(Integer petId);

    @Query("select count(pc) from PetCare pc where pc.status = :status")
    Integer countByStatus(PetCareStatus status);

}
