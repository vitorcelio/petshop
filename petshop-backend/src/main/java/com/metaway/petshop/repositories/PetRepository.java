package com.metaway.petshop.repositories;

import com.metaway.petshop.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    @Query(value = """
                select * from pets p where 1=1
                    and (:customerId is null or p.customer_id = :customerId)
                    and (:name is null or LOWER(p.name) like LOWER(CONCAT('%', :name, '%')))
                    and (:breedId is null or p.breed_id = :breedId)
                    and (CAST(:gender as character varying) is null or p.gender = CAST(:gender as character varying))
            """, nativeQuery = true)
    List<Pet> findByCustomerId(Integer customerId, String name, Integer breedId, String gender);

    @Query("select count(p) from Pet p where p.customerId = :customerId")
    Integer countPetsByCustomerId(Integer customerId);

    @Query("select count(p) from Pet p")
    Integer countAllPets();

    boolean existsByBreedId(Integer breedId);

}
