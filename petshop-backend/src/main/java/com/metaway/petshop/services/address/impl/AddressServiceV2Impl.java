package com.metaway.petshop.services.address.impl;

import com.metaway.petshop.configurations.exceptions.NotFoundException;
import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.dto.request.AddressRequestDTO;
import com.metaway.petshop.mappers.AddressDao;
import com.metaway.petshop.models.myBatis.AddressV2;
import com.metaway.petshop.services.address.AddressServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.metaway.petshop.utils.PetshopUtil.ERROR_MESSAGE;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressServiceV2Impl implements AddressServiceV2 {

    private static final String ERROR_NOT_FOUND = "Endereço não encontrado!";

    private final AddressDao dao;

    @Override
    public AddressV2 save(AddressRequestDTO request) {
        try {
            AddressV2 address = request.toAddressV2(null);
            dao.save(address);

            log.info("Endereço salva com sucesso!");
            return address;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public AddressV2 update(Integer id, AddressRequestDTO request) {

        AddressV2 address = dao.findById(id);

        if (address == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        try {
            dao.update(request.toAddressV2(address.getId()));

            AddressV2 addressUpdate = dao.findById(id);

            log.info("Endereço atualizada com sucesso!");
            return addressUpdate;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public AddressV2 findById(Integer id) {
        AddressV2 address = dao.findById(id);

        if (address == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        return address;
    }

    @Override
    public List<AddressV2> findAll(Integer customerId) {
        return dao.findAll(customerId);
    }

    @Override
    public void delete(Integer id) {
        AddressV2 address = dao.findById(id);

        if (address == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        try {
            dao.delete(address.getId());
            log.info("Endereço removido com sucesso!");
        } catch (Exception e) {
            throw new ValidationException(ERROR_MESSAGE);
        }
    }
}
