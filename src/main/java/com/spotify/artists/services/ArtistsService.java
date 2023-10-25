package com.spotify.artists.services;

import com.spotify.artists.classes.Artist;
import com.spotify.http.classes.CustomHttpRequest;
import com.spotify.http.services.RestTemplateService;
import com.spotify.http.urls.Urls;
import com.spotify.utils.JsonUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ArtistsService {

    private final RestTemplateService restTemplateService;

    public ArtistsService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    public ResponseEntity<Artist> getArtistById(String id, String token) {
        return ResponseEntity.ok().body(JsonUtils.convertJsonToPOJO(restTemplateService.createHttpRequest(new CustomHttpRequest(HttpMethod.GET, Urls.ARTISTS_URL + id, token), MediaType.APPLICATION_JSON).getBody(), Artist.class));
    }
}
