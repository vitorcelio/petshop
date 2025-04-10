package com.metaway.petshop.services.contact.impl;

import com.metaway.petshop.configurations.exceptions.NotFoundException;
import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.configurations.security.SecurityVerify;
import com.metaway.petshop.dto.request.ContactRequestDTO;
import com.metaway.petshop.enums.ContactType;
import com.metaway.petshop.mappers.ContactDao;
import com.metaway.petshop.mappers.UserDao;
import com.metaway.petshop.models.myBatis.ContactV2;
import com.metaway.petshop.models.myBatis.UserV2;
import com.metaway.petshop.services.contact.ContactServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.metaway.petshop.utils.PetshopUtil.ERROR_MESSAGE;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContactServiceV2Impl implements ContactServiceV2 {

    private static final String ERROR_USER_NOT_FOUND = "Usuário não encontrado!";
    private static final String ERROR_NOT_FOUND = "Contato não encontrado!";

    private final ContactDao dao;
    private final UserDao userDao;
    private final SecurityVerify securityVerify;

    @Override
    public ContactV2 save(ContactRequestDTO request) {
        UserV2 user = userDao.findById(request.getCustomerId());

        if (user == null) {
            throw new NotFoundException(ERROR_USER_NOT_FOUND);
        }

        securityVerify.verifyUser(user.getId());

        try {
            ContactV2 contact = request.toContactV2(null);
            contact.setCustomerId(user.getId());

            dao.save(contact);

            log.info("Contato salvo com sucesso!");
            return contact;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }

    }

    @Override
    public ContactV2 update(String uuid, ContactRequestDTO request) {
        ContactV2 contact = dao.findByUuid(uuid);

        if (contact == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        UserV2 user = userDao.findById(request.getCustomerId());

        if (user == null) {
            throw new NotFoundException(ERROR_USER_NOT_FOUND);
        }

        securityVerify.verifyUser(contact.getCustomerId());

        try {
            dao.update(request.toContactV2(contact.getUuid()));

            ContactV2 contactUpdate = dao.findByUuid(contact.getUuid());

            log.info("Contato atualizado com sucesso!");
            return contactUpdate;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public ContactV2 findByUuid(String uuid) {
        ContactV2 contact = dao.findByUuid(uuid);

        if (contact == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        return contact;
    }

    @Override
    public List<ContactV2> findAll(Integer customerId, String tag, ContactType type, LocalDateTime createdAtStart,
                                   LocalDateTime createdAtEnd, Integer page, Integer quantity) {

        page = (page == null ? 1 : page);
        quantity = (quantity == null ? 10 : quantity);

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("tag", tag);
        params.put("type", type);
        params.put("createdAtStart", createdAtStart);
        params.put("createdAtEnd", createdAtEnd);

        int offset = (page - 1) * quantity;

        params.put("quantity", quantity);
        params.put("offset", offset);

        return dao.findAll(params);
    }

    @Override
    public void delete(String uuid) {
        ContactV2 contact = dao.findByUuid(uuid);

        if (contact == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        try {
            dao.delete(contact.getUuid());

            log.info("Contato excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }
}
