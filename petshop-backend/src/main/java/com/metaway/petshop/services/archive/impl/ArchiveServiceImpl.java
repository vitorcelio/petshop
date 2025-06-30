package com.metaway.petshop.services.archive.impl;

import com.metaway.petshop.configurations.exceptions.NotFoundException;
import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.configurations.security.SecurityVerify;
import com.metaway.petshop.dto.request.ArchiveRequestDTO;
import com.metaway.petshop.models.Archive;
import com.metaway.petshop.models.Pet;
import com.metaway.petshop.models.User;
import com.metaway.petshop.repositories.ArchiveRepository;
import com.metaway.petshop.repositories.PetRepository;
import com.metaway.petshop.repositories.UserRepository;
import com.metaway.petshop.services.archive.ArchiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.metaway.petshop.utils.PetshopUtil.ERROR_MESSAGE;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArchiveServiceImpl implements ArchiveService {

    private static final String ERROR_USER_NOT_FOUND = "Usuário não encontrado!";
    private static final String ERROR_PET_NOT_FOUND = "Animal não encontrado!";
    private static final String ERROR_NOT_FOUND = "Arquivo não encontrado!";

    private final ArchiveRepository repository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final SecurityVerify securityVerify;

    @Override
    public void saveArchiveCustomer(ArchiveRequestDTO request) {

        User user = userRepository.findById(request.getObjectId()).orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

        securityVerify.verifyUser(user.getId());

        if (user.getArchiveId() != null) {
            Archive archive = repository.findById(user.getArchiveId()).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

            repository.deleteArchiveUser(archive.getId());

            repository.delete(archive);
        }

        try {
            Archive archive = Archive.builder()
                    .base64(request.getBase64())
                    .base64Mini(request.getBase64Mini())
                    .build();

            Archive save = repository.save(archive);

            user.setArchive(save);
            user.setArchiveId(save.getId());

            userRepository.save(user);

            log.info("Arquivo salvo com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao salvar arquivo de cliente: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteArchiveCustomer(Integer customerId) {

        User user = userRepository.findById(customerId).orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

        securityVerify.verifyUser(user.getId());

        Archive archive = repository.findById(user.getArchiveId()).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

        repository.deleteArchiveUser(archive.getId());

        try {
            repository.delete(archive);

            log.info("Arquivo deletado com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao deletar arquivo de cliente: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public void saveArchivePet(ArchiveRequestDTO request) {

        Pet pet = petRepository.findById(request.getObjectId()).orElseThrow(() -> new NotFoundException(ERROR_PET_NOT_FOUND));

        securityVerify.verifyUser(pet.getCustomerId());

        if (pet.getArchiveId() != null) {
            Archive archive = repository.findById(pet.getArchiveId()).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

            repository.deleteArchivePet(archive.getId());

            repository.delete(archive);
        }

        try {
            Archive archive = Archive.builder()
                    .base64(request.getBase64())
                    .base64Mini(request.getBase64Mini())
                    .build();

            Archive save = repository.save(archive);

            pet.setArchive(save);
            pet.setArchiveId(save.getId());

            petRepository.save(pet);

            log.info("Arquivo salvo com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao salvar arquivo de animal: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteArchivePet(Integer petId) {

        Pet pet = petRepository.findById(petId).orElseThrow(() -> new NotFoundException(ERROR_PET_NOT_FOUND));

        securityVerify.verifyUser(pet.getCustomerId());

        Archive archive = repository.findById(pet.getArchiveId()).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

        repository.deleteArchivePet(archive.getId());

        try {
            repository.delete(archive);

            log.info("Arquivo deletado com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao deletar arquivo de cliente: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }
}
