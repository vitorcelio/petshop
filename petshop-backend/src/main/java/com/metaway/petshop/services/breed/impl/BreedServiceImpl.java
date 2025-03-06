package com.metaway.petshop.services.breed.impl;

import com.metaway.petshop.configurations.exceptions.NotFoundException;
import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.dto.request.BreedRequestDTO;
import com.metaway.petshop.dto.response.BreedResponseDTO;
import com.metaway.petshop.models.Breed;
import com.metaway.petshop.repositories.BreedRepository;
import com.metaway.petshop.repositories.PetRepository;
import com.metaway.petshop.services.breed.BreedService;
import com.metaway.petshop.utils.PetshopUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.metaway.petshop.utils.PetshopUtil.ERROR_MESSAGE;

@Slf4j
@RequiredArgsConstructor
@Service
public class BreedServiceImpl implements BreedService {

    private final static String ERROR_NOT_FOUND = "Raça não encontrada!";

    private final BreedRepository repository;
    private final PetRepository petRepository;

    @Override
    public BreedResponseDTO save(BreedRequestDTO request) {
        try {
            Breed breed = request.toBreed();
            Breed save = repository.save(breed);

            log.info("Raça salva com sucesso!");
            return new BreedResponseDTO(save);
        } catch (Exception e) {
            log.error("Erro ao salvar raça: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public BreedResponseDTO update(Integer id, BreedRequestDTO request) {
        Breed breed = repository.findById(id).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

        try {
            breed.setName(request.getName());
            breed.setDescription(request.getDescription());
            Breed save = repository.save(breed);

            log.info("Raça atualizada com sucesso!");
            return new BreedResponseDTO(save);
        } catch (Exception e) {
            log.error("Erro ao atualizar raça: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public BreedResponseDTO findById(Integer id) {
        Breed breed = repository.findById(id).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));
        return new BreedResponseDTO(breed);
    }

    @Override
    public List<BreedResponseDTO> findAll() {
        List<Breed> breeds = repository.findAll();
        return PetshopUtil.convert(BreedResponseDTO::new, breeds);
    }

    @Override
    public void delete(Integer id) {
        Breed breed = repository.findById(id).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

        if (petRepository.existsByBreedId(breed.getId())) {
            throw new ValidationException("Existem animais cadastrados com essa raça, não é permitido excluí-la!");
        }

        try {
            repository.delete(breed);

            log.info("Raça excluída com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao excluir raça: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }
}
