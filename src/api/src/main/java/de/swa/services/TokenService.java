package de.swa.services;


import de.swa.infrastructure.entities.UserEntity;
import io.smallrye.jwt.build.Jwt;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class TokenService {
    public String getTokenForUser(UserEntity user) {
        return Jwt
                .issuer("yodel")
                .upn(user.userName)
                .subject(Long.toString(user.id))
                .sign();
    }
}
