package de.swa.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;

@ApplicationScoped
public class GoogleTokenValidator {
    public GoogleIdTokenVerifier yodelVerifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
            .setAudience(Collections.singletonList("541104707781-99oiptsrmgb78nkdhp3mngjkov9vm3ae.apps.googleusercontent.com"))
            .setIssuer("https://accounts.google.com")
            .build();
}
