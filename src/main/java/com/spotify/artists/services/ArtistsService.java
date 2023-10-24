package com.spotify.artists.services;

import com.spotify.artists.classes.Artist;
import com.spotify.http.classes.CustomHtttpRequest;
import com.spotify.http.services.WebClientService;
import com.spotify.http.urls.Urls;
import com.spotify.utils.JsonUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ArtistsService {

    private final WebClientService webClientService;

    public ArtistsService(WebClientService webClientService) {
        this.webClientService = webClientService;
    }

    public ResponseEntity<Artist> getArtistById(String id, String token) {
        return ResponseEntity.ok().body(JsonUtils.convertJsonToPOJO(webClientService.createJsonHttpRequest(new CustomHtttpRequest(HttpMethod.GET, Urls.ARTISTS_URL + id, "", token)).getBody(), Artist.class));
    }
}
