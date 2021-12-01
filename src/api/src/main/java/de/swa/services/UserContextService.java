package de.swa.services;

import de.swa.auth.GooglePrincipal;
import de.swa.infrastructure.entities.UserEntity;
import de.swa.infrastructure.repositories.UserRepository;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusPrincipal;

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

        var isQuarkusPrincipal = principal instanceof QuarkusPrincipal;
        if (isQuarkusPrincipal) {
            var userName = principal.getName();
            return userRepository.find("userName", userName).firstResult();
        }

        var isGooglePrincipal = principal instanceof GooglePrincipal;
        if (isGooglePrincipal) {
            var externalId = ((GooglePrincipal) principal).getId();
            return userRepository.find("externalId", externalId).firstResult();
        }

        return null;
    }
}
