package com.metaway.petshop.services.auth;

import com.metaway.petshop.models.User;

import java.util.Date;

public interface TokenService {

    String generateToken(User user);

    String validateToken(String token);

    Date expirationToken(String token);

}
