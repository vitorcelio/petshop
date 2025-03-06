package com.metaway.petshop.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class TokenConfiguration {

    private static final String ISSUER = "petshop";
    private static final String secret = "QXBsaWNhw6fDo28gcGFyYSBhIG1ldGF3YXkgKFRlc3RlKSAtIFBldFNob3A=";

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            return "";
        }
    }

}
