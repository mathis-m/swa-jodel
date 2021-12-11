package de.swa.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@ApplicationScoped
public class TokenValidationService {
    GoogleIdTokenVerifier googleVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
            .setAudience(Collections.singletonList("978825777932-3dgb9vtadeno8mbvs763i6tb138tuu0f.apps.googleusercontent.com"))
            .build();
    public GoogleIdToken getGoogleIdToken(String rawToken) {
        try {
            return googleVerifier.verify(rawToken);
        } catch (GeneralSecurityException | IOException e) {
            return null;
        }
    }
}
