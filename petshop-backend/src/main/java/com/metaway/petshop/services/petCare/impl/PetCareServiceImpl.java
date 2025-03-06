package com.metaway.petshop.services.petCare.impl;

import com.metaway.petshop.configurations.exceptions.NotFoundException;
import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.configurations.security.SecurityVerify;
import com.metaway.petshop.dto.request.PetCareRequestDTO;
import com.metaway.petshop.dto.response.PetCareResponseDTO;
import com.metaway.petshop.enums.PetCareStatus;
import com.metaway.petshop.models.Pet;
import com.metaway.petshop.models.PetCare;
import com.metaway.petshop.models.User;
import com.metaway.petshop.repositories.PetCareRepository;
import com.metaway.petshop.repositories.PetRepository;
import com.metaway.petshop.repositories.UserRepository;
import com.metaway.petshop.services.petCare.PetCareService;
import com.metaway.petshop.utils.PetshopUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.metaway.petshop.utils.PetshopUtil.ERROR_MESSAGE;

@Slf4j
@RequiredArgsConstructor
@Service
public class PetCareServiceImpl implements PetCareService {

    private static final String ERROR_PET_NOT_FOUND = "Animal não encontrado!";
    private static final String ERROR_USER_NOT_FOUND = "Usuário não encontrado!";
    private static final String ERROR_NOT_FOUND = "Atendimento não encontrado!";

    private final PetCareRepository repository;
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final SecurityVerify securityVerify;

    @Override
    public PetCareResponseDTO save(PetCareRequestDTO request) {
        Pet pet = petRepository.findById(request.getPetId()).orElseThrow(() -> new NotFoundException(ERROR_PET_NOT_FOUND));

        try {
            PetCare petCare = request.toPetCare();
            petCare.setPet(pet);
            petCare.setPetId(pet.getId());
            PetCare save = repository.save(petCare);

            log.info("Atendimento salvo com sucesso!");
            return new PetCareResponseDTO(save);
        } catch (Exception e) {
            log.error("Erro ao salvar atendimento: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public PetCareResponseDTO update(String uuid, PetCareRequestDTO request) {
        Pet pet = petRepository.findById(request.getPetId()).orElseThrow(() -> new NotFoundException(ERROR_PET_NOT_FOUND));
        PetCare petCare = repository.findById(uuid).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

        try {
            petCare.setPet(pet);
            petCare.setPetId(pet.getId());
            petCare.setDescription(request.getDescription());
            petCare.setPrice(request.getPrice());
            petCare.setDate(request.getDate());
            petCare.setStatus(PetCareStatus.valueOf(request.getStatus()));
            PetCare save = repository.save(petCare);

            log.info("Atendimento atualizado com sucesso!");
            return new PetCareResponseDTO(save);
        } catch (Exception e) {
            log.error("Erro ao atualizar atendimento: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public PetCareResponseDTO findByUuid(String uuid) {
        PetCare petCare = repository.findById(uuid).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));
        Pet pet = petRepository.findById(petCare.getPetId()).orElseThrow(() -> new NotFoundException(ERROR_PET_NOT_FOUND));

        securityVerify.verifyUserOrAdmin(pet.getCustomerId());

        return new PetCareResponseDTO(petCare);
    }

    @Override
    public List<PetCareResponseDTO> findAll(Integer customerId, Integer petId, String name, PetCareStatus status, LocalDateTime createAtStart, LocalDateTime createAtEnd) {
        if (customerId != null) {
            User user = userRepository.findById(customerId).orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

            securityVerify.verifyUserOrAdmin(user.getId());
        }

        String statusString = status != null ? status.name() : null;
        List<PetCare> petCares = repository.findAll(customerId, petId, name, statusString, createAtStart, createAtEnd);
        return PetshopUtil.convert(PetCareResponseDTO::new, petCares);
    }

    @Override
    public List<PetCareResponseDTO> findByPetId(Integer petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new NotFoundException(ERROR_PET_NOT_FOUND));

        securityVerify.verifyUserOrAdmin(pet.getCustomerId());

        List<PetCare> petCares = repository.findByPetId(petId);
        return PetshopUtil.convert(PetCareResponseDTO::new, petCares);
    }

    @Override
    public void delete(String uuid) {
        PetCare petCare = repository.findById(uuid).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

        try {
            repository.delete(petCare);

            log.info("Atendimento excluído com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao excluir atendimento: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }
}
