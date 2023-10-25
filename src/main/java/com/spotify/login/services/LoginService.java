package com.spotify.login.services;

import com.spotify.http.classes.CustomHttpRequest;
import com.spotify.http.services.RestTemplateService;
import com.spotify.http.urls.Urls;
import com.spotify.login.classes.Client;
import com.spotify.login.classes.Token;
import com.spotify.utils.JsonUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final RestTemplateService restTemplateService;

    public LoginService(RestTemplateService restTemplateService) {
        this.restTemplateService = restTemplateService;
    }

    public ResponseEntity<String> getBearerToken(Client client) {
        return ResponseEntity.ok().body(JsonUtils.convertJsonToPOJO(restTemplateService.createHttpRequest(new CustomHttpRequest(HttpMethod.POST, Urls.TOKEN_URL, client), MediaType.APPLICATION_FORM_URLENCODED).getBody(), Token.class).getAccessToken());
    }
}
