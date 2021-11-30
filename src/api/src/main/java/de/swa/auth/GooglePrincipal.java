package de.swa.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import javax.security.auth.Subject;
import java.security.Principal;

public class GooglePrincipal implements Principal {

    private final GoogleIdToken token;
    private final GoogleIdToken.Payload payload;

    public GooglePrincipal(GoogleIdToken token) {
        this.token = token;
        this.payload = token.getPayload();
    }

    @Override
    public String getName() {
        return payload.getEmail();
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }

    public String getId() {
        return payload.getSubject();
    }

    public GoogleIdToken getToken() {
        return token;
    }
}
