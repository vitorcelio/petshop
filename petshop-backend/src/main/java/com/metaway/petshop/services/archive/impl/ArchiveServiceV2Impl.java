package com.metaway.petshop.services.archive.impl;

import com.metaway.petshop.configurations.exceptions.NotFoundException;
import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.configurations.security.SecurityVerify;
import com.metaway.petshop.dto.request.ArchiveRequestDTO;
import com.metaway.petshop.mappers.ArchiveDao;
import com.metaway.petshop.mappers.PetDao;
import com.metaway.petshop.mappers.UserDao;
import com.metaway.petshop.models.myBatis.ArchiveV2;
import com.metaway.petshop.models.myBatis.PetV2;
import com.metaway.petshop.models.myBatis.UserV2;
import com.metaway.petshop.services.archive.ArchiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.metaway.petshop.utils.PetshopUtil.ERROR_MESSAGE;

@Slf4j
@RequiredArgsConstructor
@Service("archiveServiceV2")
public class ArchiveServiceV2Impl implements ArchiveService {

    private static final String ERROR_USER_NOT_FOUND = "Usuário não encontrado!";
    private static final String ERROR_PET_NOT_FOUND = "Animal não encontrado!";
    private static final String ERROR_NOT_FOUND = "Arquivo não encontrado!";

    private final ArchiveDao dao;
    private final UserDao userDao;
    private final PetDao petDao;
    private final SecurityVerify securityVerify;

    @Override
    public void saveArchiveCustomer(ArchiveRequestDTO request) {
        UserV2 user = userDao.findById(request.getObjectId());

        if (user == null) {
            throw new NotFoundException(ERROR_USER_NOT_FOUND);
        }

        securityVerify.verifyUser(user.getId());

        if (user.getArchiveId() != null) {
            dao.deleteArchive(request.getObjectId(), true);
        }

        try {
            ArchiveV2 archive = new ArchiveV2(request.getBase64(), request.getBase64Mini());

            dao.save(archive);

            user.setArchiveId(archive.getId());
            userDao.save(user);

            log.info("Arquivo salvo com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteArchiveCustomer(Integer customerId) {
        UserV2 user = userDao.findById(customerId);

        if (user == null) {
            throw new NotFoundException(ERROR_USER_NOT_FOUND);
        }

        ArchiveV2 archive = dao.findById(user.getArchiveId());

        if (archive == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        try {
            dao.deleteArchive(user.getArchiveId(), true);

            log.info("Arquivo deletado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }

    }

    @Override
    public void saveArchivePet(ArchiveRequestDTO request) {
        PetV2 pet = petDao.findById(request.getObjectId());

        securityVerify.verifyUser(pet.getCustomerId());

        if (pet.getArchiveId() != null) {
            dao.deleteArchive(request.getObjectId(), false);
        }

        try {
            ArchiveV2 archive = new ArchiveV2(request.getBase64(), request.getBase64Mini());

            dao.save(archive);

            pet.setArchiveId(archive.getId());
            petDao.save(pet);

            log.info("Arquivo salvo com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }

    }

    @Override
    public void deleteArchivePet(Integer petId) {
        PetV2 pet = petDao.findById(petId);

        if (pet == null) {
            throw new NotFoundException(ERROR_PET_NOT_FOUND);
        }

        securityVerify.verifyUser(pet.getCustomerId());

        ArchiveV2 archive = dao.findById(pet.getArchiveId());

        if (archive == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        try {
            dao.deleteArchive(pet.getArchiveId(), false);

            log.info("Arquivo deletado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }

    }
}
