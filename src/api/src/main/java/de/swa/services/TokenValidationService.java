package de.swa.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import de.swa.services.models.LocationResponse;
import de.swa.services.models.TokenResponse;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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

    public String getFacebookId(String token) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://graph.facebook.com/me?access_token=" + token))
                .build();
        HttpResponse<String> res = null;
        try {
            res = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        if(res == null) {
            return null;
        }

        var rawBody = res.body();
        ObjectMapper mapper = new ObjectMapper();
        try {
            var tokenRes = mapper.readValue(rawBody, TokenResponse.class);
            return tokenRes.getError() ? null : tokenRes.getId();
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
