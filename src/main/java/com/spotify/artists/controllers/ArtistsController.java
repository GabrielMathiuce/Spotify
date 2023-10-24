package com.spotify.artists.controllers;

import com.spotify.artists.classes.Artist;
import com.spotify.artists.services.ArtistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artists")
public class ArtistsController {

    private ArtistsService artistsService;

    public ArtistsController(ArtistsService artistsService) {
        this.artistsService = artistsService;
    }

    @GetMapping("/getArtistById/{id}")
    private ResponseEntity<Artist> getArtistById(@PathVariable("id") String id, @RequestParam("token") String token) {
        return artistsService.getArtistById(id, token);
    }
}
