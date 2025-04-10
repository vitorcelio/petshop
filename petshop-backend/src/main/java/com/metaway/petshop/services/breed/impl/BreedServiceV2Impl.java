package com.metaway.petshop.services.breed.impl;

import com.metaway.petshop.configurations.exceptions.NotFoundException;
import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.dto.request.BreedRequestDTO;
import com.metaway.petshop.mappers.BreedDao;
import com.metaway.petshop.models.myBatis.BreedV2;
import com.metaway.petshop.services.breed.BreedServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.metaway.petshop.utils.PetshopUtil.ERROR_MESSAGE;

@Slf4j
@Service
@RequiredArgsConstructor
public class BreedServiceV2Impl implements BreedServiceV2 {

    private final static String ERROR_NOT_FOUND = "Raça não encontrada!";

    private final BreedDao dao;

    @Override
    public BreedV2 save(BreedRequestDTO request) {
        try {

            BreedV2 breed = new BreedV2(request.getName(), request.getDescription());

            dao.save(breed);

            log.info("Raça salva com sucesso!");
            return breed;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public BreedV2 update(Integer id, BreedRequestDTO request) {

        BreedV2 breed = dao.findById(id);

        if (breed == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        try {
            dao.update(new BreedV2(breed.getId(), request.getName(), request.getDescription()));

            BreedV2 breedUpdated = dao.findById(id);

            log.info("Raça atualizada com sucesso!");
            return breedUpdated;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public BreedV2 findById(Integer id) {
        BreedV2 breed = dao.findById(id);

        if (breed == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        return breed;
    }

    @Override
    public List<BreedV2> findAll() {
        return dao.findAll();
    }

    @Override
    public void delete(Integer id) {

        BreedV2 breed = dao.findById(id);

        try {
            dao.delete(breed.getId());
            log.info("Raça removida com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }
}
