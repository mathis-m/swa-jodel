package de.swa.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import de.swa.infrastructure.entities.UserEntity;
import de.swa.infrastructure.repositories.UserRepository;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SimpleNaturalIdLoadAccess;

@ApplicationScoped
public class GoogleOrFacebookJWTValidator implements IdentityProvider<GoogleOrFacebookTokenAuthRequest> {
    @Inject
    EntityManagerFactory entityManagerFactory;
    @Inject
    GoogleTokenValidator googleValidator;

    @Override
    public Class<GoogleOrFacebookTokenAuthRequest> getRequestType() {
        return GoogleOrFacebookTokenAuthRequest.class;
    }

    @Override
    public Uni<SecurityIdentity> authenticate(GoogleOrFacebookTokenAuthRequest request,
                                              AuthenticationRequestContext context) {
        return context.runBlocking( () -> createSecurityIdentity(request));
    }

    private SecurityIdentity createSecurityIdentity(GoogleOrFacebookTokenAuthRequest request) {
        try {
            var tokenObj = request.getToken();
            GoogleIdToken idToken = googleValidator.yodelVerifier.verify(tokenObj.getToken());
            if(idToken == null) {
                return null;
            }

            EntityManager em = entityManagerFactory.createEntityManager();
            ((org.hibernate.Session) em).setHibernateFlushMode(FlushMode.MANUAL);
            ((org.hibernate.Session) em).setDefaultReadOnly(true);
            Query query = em.createQuery("SELECT u FROM UserEntity u WHERE u.externalId = :externalId");
            query.setParameter("externalId", idToken.getPayload().getSubject());
            var list = query.setMaxResults(1).getResultList();
            if(list.isEmpty()) {
                return null;
            }

            var principal = new GooglePrincipal(idToken);
            return QuarkusSecurityIdentity.builder()
                    .setPrincipal(principal)
                    .addCredential(request.getToken())
                    .addAttribute(SecurityIdentity.USER_ATTRIBUTE, principal)
                    .build();
        } catch (Exception e) {
            throw new AuthenticationFailedException(e);
        }
    }
}
