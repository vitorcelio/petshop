package com.metaway.petshop.services.user.impl;

import com.metaway.petshop.configurations.exceptions.NotFoundException;
import com.metaway.petshop.configurations.security.SecurityVerify;
import com.metaway.petshop.dto.request.*;
import com.metaway.petshop.utils.PetshopUtil;
import com.metaway.petshop.dto.response.UserResponseDTO;
import com.metaway.petshop.models.Role;
import com.metaway.petshop.models.User;
import com.metaway.petshop.repositories.RoleRepository;
import com.metaway.petshop.repositories.UserRepository;
import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.metaway.petshop.utils.PetshopUtil.ERROR_MESSAGE;

@Log4j2
@RequiredArgsConstructor
@Service("MyUserService")
public class UserServiceImpl implements UserService {

    private static final String ERROR_NOT_FOUND = "Usuário não encontrado!";
    private static final String ERROR_PROFILE_NOT_FOUND = "Perfil não encontrado!";

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final SecurityVerify securityVerify;

    @Override
    public UserResponseDTO save(UserRequestDTO request) {

        if (repository.existsByCpf(request.getCpf())) {
            throw new ValidationException("Já existe um usuário com esse CPF");
        }

        try {
            User user = request.toUser();
            User save = repository.save(user);

            log.info("Usuário salvo com sucesso!");
            return new UserResponseDTO(save);
        } catch (Exception e) {
            log.error("Erro ao salvar usuário: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }

    }

    @Override
    public UserResponseDTO update(Integer id, UserUpdateRequestDTO request) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

        if (!user.getCpf().equals(request.getCpf()) && repository.existsByCpf(request.getCpf())) {
            throw new ValidationException("Já existe um usuário com esse CPF");
        }

         try {
            user.setCpf(request.getCpf());
            user.setName(request.getName());

            User save = repository.save(user);

            log.info("Usuário atualizado com sucesso!");
            return new UserResponseDTO(save);
        } catch (Exception e) {
             log.error("Erro ao atualizar usuário: ", e);
             throw new ValidationException(ERROR_MESSAGE);
         }

    }

    @Override
    public UserResponseDTO save(UserInternalRequestDTO request) {

        if (repository.existsByCpf(request.getCpf())) {
            throw new ValidationException("Já existe um usuário com esse CPF");
        }

        Role role = roleRepository.findById(request.getRoleId()).orElseThrow(() -> new NotFoundException(ERROR_PROFILE_NOT_FOUND));

        try {
            User user = request.toUser();
            user.setRoleId(role.getId());
            User save = repository.save(user);

            log.info("Usuário salvo com sucesso!");
            return new UserResponseDTO(save);
        } catch (Exception e) {
            log.error("Erro ao salvar usuário: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }

    }

    @Override
    public UserResponseDTO update(Integer id, UserInternalUpdateRequestDTO request) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

        if (!user.getCpf().equals(request.getCpf()) && repository.existsByCpf(request.getCpf())) {
            throw new ValidationException("Já existe um usuário com esse CPF");
        }

        Role role = roleRepository.findById(request.getRoleId()).orElseThrow(() -> new NotFoundException(ERROR_PROFILE_NOT_FOUND));

        try {
            user.setCpf(request.getCpf());
            user.setName(request.getName());
            user.setEnabled(request.isEnabled());
            user.setRoleId(role.getId());
            User save = repository.save(user);

            log.info("Usuário atualizado com sucesso!");
            return new UserResponseDTO(save);
        } catch (Exception e) {
            log.error("Erro ao atualizar usuário: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }

    }

    @Override
    public UserResponseDTO findById(Integer id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));
        return new UserResponseDTO(user);
    }

    @Override
    public UserResponseDTO findByCpf(String cpf) {
        User user = repository.findByCpf(cpf).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));
        return new UserResponseDTO(user);
    }

    @Override
    public void updatePassword(Integer id, RecoverPasswordRequestDTO request) {
        User user = repository.findById(id).orElseThrow (() -> new NotFoundException(ERROR_NOT_FOUND));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (securityVerify.isCustomer() || securityVerify.getLoggedUserId().equals(user.getId())) {
            if (!encoder.matches(request.getOldPassword(), user.getPassword())) {
                throw new ValidationException("A senha antiga está incorreta!");
            }
        }

        try {
            user.setPassword(encoder.encode(request.getNewPassword()));
            repository.save(user);

            log.info("Senha atualizada com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao atualizar senha: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }

    @Override
    public List<UserResponseDTO> findAll(Integer roleId, String name, String cpf, LocalDateTime createdAtStart, LocalDateTime createdAtEnd) {
        List<User> users = repository.findByUsers(roleId, name, cpf, createdAtStart, createdAtEnd);
        return PetshopUtil.convert(UserResponseDTO::new, users);
    }

    @Override
    public void delete(Integer id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException(ERROR_NOT_FOUND));

        try {
            user.setEnabled(false);
            user.setAccountDeleted(true);
            repository.save(user);

            log.info("Usuário excluído com sucesso!");
        } catch (Exception e) {
            log.error("Erro ao excluir usuário: ", e);
            throw new ValidationException(ERROR_MESSAGE);
        }
    }


}
