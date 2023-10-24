package com.spotify.http.services;

import com.spotify.http.classes.CustomHtttpRequest;
import com.spotify.utils.ObjectMapperUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class WebClientService {

    WebClient webClient = WebClient.create();

    public ResponseEntity<String> createJsonHttpRequest(CustomHtttpRequest customHtttpRequest)  {
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.method(customHtttpRequest.getMethod());
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(customHtttpRequest.getUrl());
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(customHtttpRequest.getBody());
        if (customHtttpRequest.getToken() != null) {
            headersSpec = headersSpec.header("Authorization", "Bearer " + customHtttpRequest.getToken());
        }

        WebClient.ResponseSpec responseSpec = headersSpec
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve();

        return responseSpec.toEntity(String.class).block();
    }

    public ResponseEntity<String> createFormUrlencodedHttpRequest(CustomHtttpRequest customHtttpRequest)  {
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.method(customHtttpRequest.getMethod());
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(customHtttpRequest.getUrl());
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.body(BodyInserters.fromFormData(convertPOJOToUrlencodedBody(customHtttpRequest.getBody())));
        if (customHtttpRequest.getToken() != null) {
            headersSpec = headersSpec.header("Authorization", "Bearer " + customHtttpRequest.getToken());
        }

        WebClient.ResponseSpec responseSpec = headersSpec
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .retrieve();

        return responseSpec.toEntity(String.class).block();
    }

    private MultiValueMap<String, String> convertPOJOToUrlencodedBody(Object pojo) {
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.setAll(ObjectMapperUtils.convertValue(pojo, Map.class));
        return valueMap;
    }
}
