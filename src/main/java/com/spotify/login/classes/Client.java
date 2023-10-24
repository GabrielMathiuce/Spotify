package com.spotify.login.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Client {

    @JsonProperty("grant_type")
    private String grantType;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;
}
