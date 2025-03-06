package com.metaway.petshop.services.pet.impl;

import com.metaway.petshop.configurations.exceptions.NotFoundException;
import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.configurations.security.SecurityVerify;
import com.metaway.petshop.dto.request.PetRequestDTO;
import com.metaway.petshop.dto.response.PetResponseDTO;
import com.metaway.petshop.enums.Gender;
import com.metaway.petshop.models.Breed;
import com.metaway.petshop.models.Pet;
import com.metaway.petshop.models.User;
import com.metaway.petshop.repositories.BreedRepository;
import com.metaway.petshop.repositories.PetCareRepository;
import com.metaway.petshop.repositories.PetRepository;
import com.metaway.petshop.repositories.UserRepository;
import com.metaway.petshop.services.pet.PetService;
import com.metaway.petshop.utils.PetshopUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.metaway.petshop.utils.PetshopUtil.ERROR_MESSAGE;

@Slf4j
@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {

    private static final String ERROR_USER_NOT_FOUND = "Usuário não encontrado!";
    private static final String ERROR_BREED_NOT_FOUND = "Raça não encontrada!";
    private static final String ERROR_NOT_FOUND = "Animal não encontrado!";

    private final PetRepository repository;
    private final UserRepository userRepository;
    private final BreedRepository breedRepository;
    private final PetCareRepository petCareRepository;
    private final SecurityVerify securityVerify;

    @Override
    public PetResponseDTO save(PetRequestDTO request) {
        User user = userRepository.findById(request.getCustomerId()).orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

        securityVerify.verifyUser(user.getId());

        Breed breed = breedRepository.findById(request.getBreedId()).orElseThrow(() -> new NotFoundException(ERROR_BREED_NOT_FOUND));

        try {
            Pet pet = request.toPet();
            pet.setCustomer(user);
            pet.setCustomerId(user.getId());
            pet.setBreed(breed);
            pet.setBreedId(breed.getId());
            Pet save = repository.save(pet);

            log.info("Animal salvo com sucesso!");
            return new PetResponseDTO(save);
        } catch (Exception e) {
            log.error("Erro ao salvar animal: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public PetResponseDTO update(Integer id, PetRequestDTO request) {
        User user = userRepository.findById(request.getCustomerId()).orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

        securityVerify.verifyUser(user.getId());

        Breed breed = breedRepository.findById(request.getBreedId()).orElseThrow(() -> new NotFoundException(ERROR_BREED_NOT_FOUND));

        Pet pet = repository.findById(id).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

        try {
            pet.setCustomer(user);
            pet.setCustomerId(user.getId());
            pet.setBreed(breed);
            pet.setBreedId(breed.getId());
            pet.setName(request.getName());
            pet.setBirthDate(request.getBirthDate());
            pet.setGender(Gender.valueOf(request.getGender()));
            Pet save = repository.save(pet);

            log.info("Animal atualizado com sucesso!");
            return new PetResponseDTO(save);
        } catch (Exception e) {
            log.error("Erro ao atualizar animal: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public PetResponseDTO findById(Integer id) {
        Pet pet = repository.findById(id).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

        securityVerify.verifyUserOrAdmin(pet.getCustomerId());

        return new PetResponseDTO(pet);
    }

    @Override
    public List<PetResponseDTO> findAll(Integer customerId, String name, Integer breedId, Gender gender) {
        if (customerId != null) {
            User user = userRepository.findById(customerId).orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

            securityVerify.verifyUserOrAdmin(user.getId());
        }

        String genderString = gender != null ? gender.name() : null;
        List<Pet> pets = repository.findByCustomerId(customerId, name, breedId, genderString);
        return PetshopUtil.convert(PetResponseDTO::new, pets);
    }

    @Override
    public void delete(Integer id) {
        Pet pet = repository.findById(id).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

        try {
            securityVerify.verifyUser(pet.getCustomerId());

            petCareRepository.deleteByPetId(pet.getId());

            repository.delete(pet);

            log.info("Animal excluído com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao excluir animal: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }
}
