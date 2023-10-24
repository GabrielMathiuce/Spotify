package com.spotify.artists.services;

import com.spotify.artists.classes.Artist;
import com.spotify.http.WebClientService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ArtistsService {

    private WebClientService webClientService;

    public ArtistsService(WebClientService webClientService) {
        this.webClientService = webClientService;
    }

    public ResponseEntity<Artist> getArtistById(String id, String token) {
        return new ResponseEntity<>(webClientService.createHttpRequest(HttpMethod.GET, "https://api.spotify.com/v1/artists/" + id, "", token), HttpStatus.OK);
    }
}
