package com.metaway.petshop.services.contact.impl;

import com.metaway.petshop.configurations.exceptions.NotFoundException;
import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.configurations.security.SecurityVerify;
import com.metaway.petshop.dto.request.ContactRequestDTO;
import com.metaway.petshop.dto.response.ContactResponseDTO;
import com.metaway.petshop.enums.ContactType;
import com.metaway.petshop.models.Contact;
import com.metaway.petshop.models.User;
import com.metaway.petshop.repositories.ContactRepository;
import com.metaway.petshop.repositories.UserRepository;
import com.metaway.petshop.services.contact.ContactService;
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
public class ContactServiceImpl implements ContactService {

    private static final String ERROR_USER_NOT_FOUND = "Usuário não encontrado!";
    private static final String ERROR_NOT_FOUND = "Contato não encontrado!";

    private final ContactRepository repository;
    private final UserRepository userRepository;
    private final SecurityVerify securityVerify;

    @Override
    public ContactResponseDTO save(ContactRequestDTO request) {
        User user = userRepository.findById(request.getCustomerId()).orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

        securityVerify.verifyUser(user.getId());

        try {
            Contact contact = request.toContact();
            contact.setCustomer(user);
            contact.setCustomerId(user.getId());
            Contact save = repository.save(contact);

            log.info("Contato salvo com sucesso!");
            return new ContactResponseDTO(save);
        } catch (Exception e) {
            log.error("Erro ao salvar contato: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public ContactResponseDTO update(String uuid, ContactRequestDTO request) {
        Contact contact = repository.findById(uuid).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));
        User user = userRepository.findById(request.getCustomerId()).orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

        securityVerify.verifyUser(user.getId());

        try {
            contact.setCustomer(user);
            contact.setCustomerId(user.getId());
            contact.setTag(request.getTag());
            contact.setType(ContactType.valueOf(request.getType()));
            contact.setValue(request.getValue());
            contact.setMessage(request.getMessage());
            Contact save = repository.save(contact);

            log.info("Contato atualizado com sucesso!");
            return new ContactResponseDTO(save);
        } catch (Exception e) {
            log.error("Erro ao atualizar contato: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public ContactResponseDTO findByUuid(String uuid) {
        Contact contact = repository.findById(uuid).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

        securityVerify.verifyUserOrAdmin(contact.getCustomerId());

        return new ContactResponseDTO(contact);
    }

    @Override
    public List<ContactResponseDTO> findAll(Integer customerId, String tag, ContactType type, LocalDateTime createdAtStart, LocalDateTime createdAtEnd) {
        if (customerId != null) {
            User user = userRepository.findById(customerId).orElseThrow(() -> new NotFoundException(ERROR_USER_NOT_FOUND));

            securityVerify.verifyUserOrAdmin(user.getId());
        }

        String typeString = type != null ? type.name() : null;
        List<Contact> customers = repository.findByCustomerId(customerId, tag, typeString, createdAtStart, createdAtEnd);
        return PetshopUtil.convert(ContactResponseDTO::new, customers);
    }

    @Override
    public void delete(String uuid) {
        Contact contact = repository.findById(uuid).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

        securityVerify.verifyUser(contact.getCustomerId());

        try {
            repository.delete(contact);

            log.info("Contato excluído com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao excluir contato: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }
}
