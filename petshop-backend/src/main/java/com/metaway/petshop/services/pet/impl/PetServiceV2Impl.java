package com.metaway.petshop.services.pet.impl;

import com.metaway.petshop.configurations.exceptions.NotFoundException;
import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.dto.request.PetRequestDTO;
import com.metaway.petshop.enums.Gender;
import com.metaway.petshop.mappers.PetDao;
import com.metaway.petshop.models.myBatis.PetV2;
import com.metaway.petshop.services.pet.PetServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.metaway.petshop.utils.PetshopUtil.ERROR_MESSAGE;

@Slf4j
@RequiredArgsConstructor
@Service
public class PetServiceV2Impl implements PetServiceV2 {

    private final static String ERROR_NOT_FOUND = "Animal de estimação não encontrado!";

    private final PetDao dao;

    @Override
    public PetV2 save(PetRequestDTO request) {
        try {
            PetV2 pet = request.toPetV2(null);

            dao.save(pet);

            log.info("Animal de estimação salvo com sucesso!");
            return pet;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public PetV2 update(Integer id, PetRequestDTO request) {

        PetV2 pet = dao.findById(id);

        if (pet == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        try {
            dao.update(request.toPetV2(pet.getId()));

            PetV2 petUpdate = dao.findById(id);

            log.info("Animal de estimação atualizado com sucesso!");
            return petUpdate;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public PetV2 findById(Integer id) {
        PetV2 pet = dao.findById(id);

        if (pet == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        return pet;
    }

    @Override
    public List<PetV2> findAll(Integer customerId, String name, Integer breedId, Gender gender) {
        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("name", name);
        params.put("breedId", breedId);
        params.put("gender", gender);

        return dao.findAll(params);
    }

    @Override
    public void delete(Integer id) {
        PetV2 pet = dao.findById(id);

        if (pet == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        try {
            dao.delete(id);
            log.info("Animal de estimação atualizado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }
}
