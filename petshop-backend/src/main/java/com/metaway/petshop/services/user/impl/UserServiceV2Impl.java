package com.metaway.petshop.services.user.impl;

import com.metaway.petshop.configurations.activemq.producer.ProducerJms;
import com.metaway.petshop.configurations.exceptions.NotFoundException;
import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.configurations.security.SecurityVerify;
import com.metaway.petshop.dto.request.*;
import com.metaway.petshop.mappers.RoleDao;
import com.metaway.petshop.mappers.UserDao;
import com.metaway.petshop.models.myBatis.RoleV2;
import com.metaway.petshop.models.myBatis.UserV2;
import com.metaway.petshop.services.user.UserServiceV2;
import com.metaway.petshop.utils.JsonUtil;
import com.metaway.petshop.utils.PetshopUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.metaway.petshop.utils.PetshopUtil.ERROR_MESSAGE;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceV2Impl implements UserServiceV2 {

    private static final String ERROR_NOT_FOUND = "Usuário não encontrado";
    private static final String ERROR_PROFILE_NOT_FOUND = "Perfil não encontrado!";

    private final UserDao dao;
    private final RoleDao roleDao;
    private final SecurityVerify securityVerify;
    private final ProducerJms producer;
    private final JsonUtil jsonUtil;

    @Override
    public UserV2 save(UserRequestDTO request) {

        if (dao.existCPF(request.getCpf())) {
            throw new ValidationException("Já existe um usuário com esse CPF");
        }

        try {
            UserV2 user = request.toUserV2(null);

            dao.save(user);

            log.info("Usuário salvo com sucesso!");

            producer.send(PetshopUtil.CONSUMER_EMAIL, jsonUtil.toJson(user));
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public UserV2 update(Integer id, UserUpdateRequestDTO request) {

        UserV2 user = dao.findById(id);

        if (user == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        if (!user.getCpf().equals(request.getCpf()) && dao.existCPF(request.getCpf())) {
            throw new ValidationException("Já existe um usuário com esse CPF");
        }

        try {
            user.setCpf(request.getCpf());
            user.setName(request.getName());

            dao.update(user);

            UserV2 userUpdate = dao.findById(user.getId());

            log.info("Usuário atualizado com sucesso!");
            return userUpdate;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public UserV2 save(UserInternalRequestDTO request) {

        if (dao.existCPF(request.getCpf())) {
            throw new ValidationException("Já existe um usuário com esse CPF");
        }

        try {
            UserV2 user = request.toUserV2(null);

            dao.save(user);

            log.info("Usuário salvo com sucesso!");
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public UserV2 update(Integer id, UserInternalUpdateRequestDTO request) {

        UserV2 user = dao.findById(id);

        if (user == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        if (!user.getCpf().equals(request.getCpf()) && dao.existCPF(request.getCpf())) {
            throw new ValidationException("Já existe um usuário com esse CPF");
        }

        RoleV2 role = roleDao.findById(request.getRoleId());

        if (role == null) {
            throw new NotFoundException(ERROR_PROFILE_NOT_FOUND);
        }

        try {
            user.setCpf(request.getCpf());
            user.setName(request.getName());
            user.setEnabled(request.isEnabled());
            user.setRoleId(request.getRoleId());

            dao.update(user);

            UserV2 userUpdate = dao.findById(user.getId());

            log.info("Usuário atualizado com sucesso!");
            return userUpdate;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public UserV2 findById(Integer id) {
        UserV2 user = dao.findById(id);

        if (user == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        return user;
    }

    @Override
    public UserV2 findByCpf(String cpf) {
        UserV2 user = dao.findByCpf(cpf);

        if (user == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        return user;
    }

    @Override
    public void updatePassword(Integer id, RecoverPasswordRequestDTO request) {
        UserV2 user = dao.findById(id);

        if (user == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (securityVerify.isCustomer() || securityVerify.getLoggedUserId().equals(user.getId())) {
            if (!encoder.matches(request.getOldPassword(), user.getPassword())) {
                throw new ValidationException("A senha antiga está incorreta!");
            }
        }

        try {
            user.setPassword(encoder.encode(request.getNewPassword()));
            dao.update(user);

            log.info("Senha atualizada com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public List<UserV2> findAll(Integer roleId, String name, String cpf, LocalDateTime createdAtStart,
                                LocalDateTime createdAtEnd, Integer page, Integer quantity) {

        page = (page == null) ? 1 : page;
        quantity = (quantity == null) ? 10 : quantity;

        Map<String, Object> params = new HashMap<>();
        params.put("roleId", roleId);
        params.put("name", name);
        params.put("cpf", cpf);
        params.put("createdAtStart", createdAtStart);
        params.put("createdAtEnd", createdAtEnd);


        int offset = (page - 1) * quantity;

        params.put("quantity", quantity);
        params.put("offset", offset);

        return dao.findAll(params);
    }

    @Override
    public List<UserV2> findByIds(String ids) {
        List<String> listString = List.of(ids.split(","));

        try {
            List<Integer> list = listString.stream().map(Integer::parseInt).toList();
            return dao.findByIds(list);
        } catch (Exception e) {
            throw new NotFoundException("Erro nos ids, só é permitido passar números inteiros");
        }

    }

    @Override
    public void delete(Integer id) {

        UserV2 user = dao.findById(id);

        if (user == null) {
            throw new NotFoundException(ERROR_NOT_FOUND);
        }

        try {
            dao.delete(user.getId());

            log.info("Usuário excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValidationException(ERROR_MESSAGE);
        }
    }
}
