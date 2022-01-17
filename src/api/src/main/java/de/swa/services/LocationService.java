package de.swa.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.swa.services.models.LocationResponse;

import javax.enterprise.context.RequestScoped;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequestScoped
public class LocationService {
    public String getLocation(Double lat, Double lon) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://nominatim.openstreetmap.org/reverse?lat=" + lat + "&lon=" + lon + "&format=json"))
                .build();
        HttpResponse<String> res = null;
        try {
            res = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(res == null) {
            return "No Location";
        }

        var rawBody = res.body();
        ObjectMapper mapper = new ObjectMapper();
        try {
            var location = mapper.readValue(rawBody, LocationResponse.class);
            var locationText = location.getAddress().getVillage();
            if(locationText == null) {
                locationText = location.getAddress().getSuburb();
            }
            if(locationText == null) {
                locationText = location.getAddress().getCity();
            }
            if(locationText == null) {
                locationText = location.getAddress().getCounty();
            }
            if(locationText == null) {
                locationText = location.getAddress().getState();
            }
            if(locationText == null) {
                locationText = location.getAddress().getCounty();
            }
            return locationText;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "No Location";
        }
    }
}
