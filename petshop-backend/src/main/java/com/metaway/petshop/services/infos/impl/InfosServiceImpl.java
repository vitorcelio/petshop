package com.metaway.petshop.services.infos.impl;

import com.metaway.petshop.configurations.exceptions.NotFoundException;
import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.dto.response.InfosResponseDTO;
import com.metaway.petshop.enums.PetCareStatus;
import com.metaway.petshop.enums.RolesEnum;
import com.metaway.petshop.models.User;
import com.metaway.petshop.repositories.ContactRepository;
import com.metaway.petshop.repositories.PetCareRepository;
import com.metaway.petshop.repositories.PetRepository;
import com.metaway.petshop.repositories.UserRepository;
import com.metaway.petshop.services.infos.InfosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.metaway.petshop.utils.PetshopUtil.ERROR_MESSAGE;

@Slf4j
@RequiredArgsConstructor
@Service
public class InfosServiceImpl implements InfosService {

    private final PetRepository petRepository;
    private final PetCareRepository petCareRepository;
    private final UserRepository userRepository;
    private final ContactRepository contactRepository;

    @Override
    public InfosResponseDTO getInfosAdmin() {
        try {
            var infos = InfosResponseDTO.builder()
                    .petNumber(petRepository.countAllPets())
                    .petCareNumber(petCareRepository.countByStatus(PetCareStatus.COMPLETED))
                    .petCareNumberPending(petCareRepository.countByStatus(PetCareStatus.PENDING))
                    .petCareNumberCanceled(petCareRepository.countByStatus(PetCareStatus.CANCELLED))
                    .customerNumber(userRepository.countUserByRoleId(RolesEnum.CUSTOMER.getId()))
                    .contactsNumber(contactRepository.countAllContacts())
                    .build();

            log.info("Informações buscadas com sucesso!");
            return infos;
        } catch (Exception e) {
            log.error("Erro ao buscar infos de administrador: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public InfosResponseDTO getInfosCustomer(Integer customerId) {

        User user = userRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));

        try {
            var infos = InfosResponseDTO.builder()
                    .petNumber(petRepository.countPetsByCustomerId(user.getId()))
                    .contactsNumber(contactRepository.countByCustomerId(user.getId()))
                    .build();

            log.info("Informações buscadas com sucesso!");
            return infos;
        } catch (Exception e) {
            log.error("Erro ao buscar infos de cliente: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }
}
