package de.swa.auth;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.credential.PasswordCredential;
import io.quarkus.security.credential.TokenCredential;
import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.security.identity.request.BaseAuthenticationRequest;
import io.quarkus.security.identity.request.UsernamePasswordAuthenticationRequest;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.quarkus.vertx.http.runtime.security.HttpCredentialTransport;
import io.quarkus.vertx.http.runtime.security.HttpSecurityUtils;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Alternative
@Priority(2)
@ApplicationScoped
public class CustomAuthMechanism implements HttpAuthenticationMechanism {
    protected static final String BEARER = "Bearer";
    protected static final String BASIC = "Basic";
    protected static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public Uni<SecurityIdentity> authenticate(RoutingContext context, IdentityProviderManager identityProviderManager) {
        Uni<SecurityIdentity> optional = Uni.createFrom().optional(Optional.empty());

        var authHeader = context
                .request()
                .headers()
                .get(AUTHORIZATION_HEADER);

        if(authHeader == null) {
            return optional;
        }

        var parts = authHeader.split(" ");
        BaseAuthenticationRequest request;
        var value = parts[1];
        switch (parts[0]) {
            case BEARER:
                request = new GoogleOrFacebookTokenAuthRequest(new TokenCredential(parts[1], BEARER));
                break;
            case BASIC:
                byte[] decode = Base64.getDecoder().decode(value);
                var plainValue = new String(decode, Charset.defaultCharset());
                int colonPos;
                if ((colonPos = plainValue.indexOf(":")) > -1) {
                    String userName = plainValue.substring(0, colonPos);
                    char[] password = plainValue.substring(colonPos + 1).toCharArray();

                    UsernamePasswordAuthenticationRequest credential = new UsernamePasswordAuthenticationRequest(userName,
                            new PasswordCredential(password));
                    HttpSecurityUtils.setRoutingContextAttribute(credential, context);
                    request = credential;
                    break;
                }
                return Uni.createFrom().failure(new AuthenticationFailedException());
            default:
                return optional;
        }
        Uni<SecurityIdentity> id;
        try {
            id = identityProviderManager.authenticate(request);
        } catch (IllegalArgumentException ex) {
            if(ex.getMessage().contains("cryptString")) {
                return Uni.createFrom().failure(new AuthenticationFailedException());
            }
            throw ex;
        }
        return id;
    }

    @Override
    public Uni<ChallengeData> getChallenge(RoutingContext context) {
        ChallengeData result = new ChallengeData(
                HttpResponseStatus.UNAUTHORIZED.code(),
                HttpHeaderNames.WWW_AUTHENTICATE,
                "Bearer, Basic");
        return Uni.createFrom().item(result);
    }

    @Override
    public Set<Class<? extends AuthenticationRequest>> getCredentialTypes() {
        return Collections.singleton(GoogleOrFacebookTokenAuthRequest.class);
    }

    @Override
    public HttpCredentialTransport getCredentialTransport() {
        return new HttpCredentialTransport(HttpCredentialTransport.Type.AUTHORIZATION, BEARER);
    }
}
