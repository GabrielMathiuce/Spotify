package com.spotify.login.services;

import com.spotify.http.classes.CustomHtttpRequest;
import com.spotify.http.services.WebClientService;
import com.spotify.http.urls.Urls;
import com.spotify.login.classes.Client;
import com.spotify.login.classes.Token;
import com.spotify.utils.JsonUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final WebClientService webClientService;

    public LoginService(WebClientService webClientService) {
        this.webClientService = webClientService;
    }

    public ResponseEntity<String> getBearerToken(Client client) {
        return ResponseEntity.ok().body(JsonUtils.convertJsonToPOJO(webClientService.createFormUrlencodedHttpRequest(new CustomHtttpRequest(HttpMethod.POST, Urls.TOKEN_URL, client)).getBody(), Token.class).getAccessToken());
    }
}
