package com.danfy.get_a_pet.services;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.danfy.get_a_pet.domain.entities.models.User;
import com.danfy.get_a_pet.domain.services.ITokenService;
import com.danfy.get_a_pet.dtos.DecodedTokenDTO;
import com.danfy.get_a_pet.exceptions.auth.ExpiredTokenException;

@Service
public class JavaJWTService implements ITokenService{

    @Value("${jwt.public.key}")
    private RSAPublicKey publicKey;
    @Value("${jwt.private.key}")
    private RSAPrivateKey privateKey;
    @Override
    public String create(User user) {
        try {
            Algorithm algorithm = Algorithm.RSA256(null, privateKey);
            var now = Instant.now();
            String token = JWT.create()
                    .withIssuer("get_a_pet_api")
                    .withSubject(user.getEmail())
                    .withClaim("user_id", user.getId())
                    .withIssuedAt(now)
                    .withExpiresAt(now.plusSeconds(3600))
                    .sign(algorithm);
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Error encoding token");
        }
    }

    @Override
    public DecodedTokenDTO validate(String token) {
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            var decodedToken = JWT.require(algorithm)
                    .withIssuer("get_a_pet_api")
                    .build()
                    .verify(token);
            String email = decodedToken.getSubject();
            String userId = decodedToken.getClaim("user_id").asString();
            return new DecodedTokenDTO(userId, email);
        } catch (JWTVerificationException e) {
            throw new ExpiredTokenException(e.getMessage());
        }
    }
}
