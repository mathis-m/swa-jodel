package de.swa.services;

import de.swa.infrastructure.entities.UserEntity;
import de.swa.infrastructure.repositories.UserRepository;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class UserContextService {
    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    UserRepository userRepository;

    public UserEntity getCurrentUser() {
        var principal = securityIdentity.getPrincipal();
        if (principal == null)
            return null;

        var jwtPrinciple = principal instanceof DefaultJWTCallerPrincipal;
        if (jwtPrinciple) {
            var userName = principal.getName();
            return userRepository.find("userName", userName).firstResult();
        }
        return null;
    }
}
