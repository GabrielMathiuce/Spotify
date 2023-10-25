package com.spotify.login.controllers;

import com.spotify.login.classes.Client;
import com.spotify.login.services.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/getBearerToken")
    private ResponseEntity<String> getBearerToken(@RequestBody Client client) {
        return loginService.getBearerToken(client);
    }

}
