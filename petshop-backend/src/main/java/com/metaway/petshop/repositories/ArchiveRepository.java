package com.metaway.petshop.repositories;

import com.metaway.petshop.models.Archive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, Integer> {

    @Transactional
    @Modifying
    @Query("update User set archiveId = null where archiveId = :id")
    void deleteArchiveUser(Integer id);

    @Transactional
    @Modifying
    @Query("update Pet set archiveId = null where archiveId = :id")
    void deleteArchivePet(Integer id);

}
