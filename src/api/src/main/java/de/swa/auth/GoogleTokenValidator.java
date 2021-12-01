package de.swa.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;

@ApplicationScoped
public class GoogleTokenValidator {
    public GoogleIdTokenVerifier yodelVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
            .setAudience(Collections.singletonList("978825777932-3dgb9vtadeno8mbvs763i6tb138tuu0f.apps.googleusercontent.com"))
            .build();
}
