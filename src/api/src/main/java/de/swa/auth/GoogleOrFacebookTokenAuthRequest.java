package de.swa.auth;

import io.quarkus.security.credential.TokenCredential;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.security.identity.request.BaseAuthenticationRequest;

public class GoogleOrFacebookTokenAuthRequest extends BaseAuthenticationRequest implements AuthenticationRequest {
    private final TokenCredential token;

    public GoogleOrFacebookTokenAuthRequest(TokenCredential token) {
        this.token = token;
    }

    public TokenCredential getToken() {
        return this.token;
    }
}
