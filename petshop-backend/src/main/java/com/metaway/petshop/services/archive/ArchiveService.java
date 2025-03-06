package com.metaway.petshop.services.archive;

import com.metaway.petshop.dto.request.ArchiveRequestDTO;

public interface ArchiveService {

    void saveArchiveCustomer(ArchiveRequestDTO request);

    void deleteArchiveCustomer(Integer id);

    void saveArchivePet(ArchiveRequestDTO request);

    void deleteArchivePet(Integer id);

}
