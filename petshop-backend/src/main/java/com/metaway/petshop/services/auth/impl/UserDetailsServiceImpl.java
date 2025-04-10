package com.metaway.petshop.services.auth.impl;

import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("MyUserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return repository.findByCpf(cpf).orElseThrow(() -> new ValidationException("Cpf ou Senha incorreta!"));
    }
}
