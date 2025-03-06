package com.metaway.petshop.services.address.impl;

import com.metaway.petshop.configurations.exceptions.NotFoundException;
import com.metaway.petshop.configurations.security.SecurityVerify;
import com.metaway.petshop.utils.PetshopUtil;
import com.metaway.petshop.dto.request.AddressRequestDTO;
import com.metaway.petshop.dto.response.AddressResponseDTO;
import com.metaway.petshop.models.Address;
import com.metaway.petshop.models.User;
import com.metaway.petshop.repositories.AddressRepository;
import com.metaway.petshop.repositories.UserRepository;
import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.services.address.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.metaway.petshop.utils.PetshopUtil.ERROR_MESSAGE;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final static String ERROR_USER_NOT_FOUND = "Usuário não encontrado!";
    private final static String ERROR_NOT_FOUND = "Endereço não encontrado!";

    private final AddressRepository repository;
    private final UserRepository userRepository;
    private final SecurityVerify securityVerify;

    @Override
    public AddressResponseDTO save(AddressRequestDTO request) {
        User user = userRepository.findById(request.getCustomerId()).orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

        securityVerify.verifyUser(user.getId());

        try {
            Address address = request.toAddress();
            address.setCustomerId(user.getId());
            Address save = repository.save(address);

            log.info("Endereço salvo com sucesso!");
            return new AddressResponseDTO(save);
        } catch (RuntimeException e) {
            log.error("Erro ao salvar endereço: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public AddressResponseDTO update(Integer id, AddressRequestDTO request) {
        Address address = repository.findById(id).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));
        User user = userRepository.findById(request.getCustomerId()).orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

        securityVerify.verifyUser(user.getId());

        try {

            address.setCustomerId(user.getId());
            address.setStreet(request.getStreet());
            address.setDistrict(request.getDistrict());
            address.setComplement(request.getComplement());
            address.setCity(request.getCity());
            address.setState(request.getState());
            address.setPostalCode(request.getPostalCode());
            address.setTag(request.getTag());
            Address save = repository.save(address);

            log.info("Endereço atualizado com sucesso!");
            return new AddressResponseDTO(save);
        } catch (Exception e) {
            log.error("Erro ao atualizar endereço: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public AddressResponseDTO findById(Integer id) {
        Address address = repository.findById(id).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

        securityVerify.verifyUserOrAdmin(address.getCustomerId());

        return new AddressResponseDTO(address);
    }

    @Override
    public List<AddressResponseDTO> findAll(Integer customerId) {
        User user = userRepository.findById(customerId).orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

        securityVerify.verifyUserOrAdmin(user.getId());

        List<Address> address = repository.findByCustomerId(user.getId());
        return PetshopUtil.convert(AddressResponseDTO::new, address);
    }

    @Override
    public void delete(Integer id) {
        Address address = repository.findById(id).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

        securityVerify.verifyUser(address.getCustomerId());

        try {

            repository.delete(address);

            log.info("Endereço excluído com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao excluir endereço: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

}
