package com.metaway.petshop.repositories;

import com.metaway.petshop.models.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {

    @Query(value = """
                select * from contacts c where 1=1
                    and (:customerId is null or c.customer_id = :customerId)
                    and (:tag is null or c.tag = :tag)
                    and (CAST(:type as character varying) is null or c.type = CAST(:type as character varying))
                    and (CAST(:createdAtStart as timestamp) is null or c.created_at >= CAST(:createdAtStart as timestamp))
                    and (CAST(:createdAtEnd as timestamp) is null or c.created_at <= CAST(:createdAtEnd as timestamp))
            """, nativeQuery = true)
    List<Contact> findByCustomerId(Integer customerId, String tag, String type, LocalDateTime createdAtStart,
                                   LocalDateTime createdAtEnd);

    @Query("select count(c) from Contact c where c.customerId = :customerId")
    Integer countByCustomerId(Integer customerId);

    @Query("select count(c) from Contact c")
    Integer countAllContacts();

}
