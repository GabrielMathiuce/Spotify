package com.spotify.http.services;

import com.spotify.http.classes.CustomHttpRequest;
import com.spotify.http.classes.CustomResponseErrorHandler;
import com.spotify.login.classes.Client;
import com.spotify.utils.ObjectMapperUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class RestTemplateService {

    RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    public ResponseEntity<String> createJsonHttpRequest(CustomHttpRequest customHttpRequest)  {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(customHttpRequest.getUrl());

        restTemplate.setErrorHandler(new CustomResponseErrorHandler());

        setHeaders(customHttpRequest, MediaType.APPLICATION_JSON);
        setQueryParams(customHttpRequest, uriComponentsBuilder);
        setToken(customHttpRequest);

        HttpEntity<Object> entity = new HttpEntity<>(customHttpRequest.getBody(), customHttpRequest.getHttpHeaders());
        return restTemplate.exchange(uriComponentsBuilder.toUriString(), customHttpRequest.getMethod(), entity, String.class);
    }

    public ResponseEntity<String> createUrlEncodedHttpRequest(CustomHttpRequest customHttpRequest) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(customHttpRequest.getUrl());

        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
        setHeaders(customHttpRequest, MediaType.APPLICATION_FORM_URLENCODED);
        setToken(customHttpRequest);
        setUrlEncodedBody(customHttpRequest);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(customHttpRequest.getUrlEncodedBody(), httpHeaders);
        return restTemplate.exchange(uriComponentsBuilder.toUriString(), customHttpRequest.getMethod(), entity, String.class);
    }

    private void setHeaders(CustomHttpRequest customHttpRequest, MediaType mediaType) {
        if(customHttpRequest.getHttpHeaders() == null) {
            customHttpRequest.setHttpHeaders(new HttpHeaders());
            customHttpRequest.getHttpHeaders().setContentType(mediaType);
        }
        customHttpRequest.getHttpHeaders().setContentType(mediaType);
    }

    private void setQueryParams(CustomHttpRequest customHttpRequest, UriComponentsBuilder uriComponentsBuilder) {
        if(customHttpRequest.getQueryParams() != null) {
            customHttpRequest.getQueryParams().forEach(uriComponentsBuilder::queryParam);
        }
    }

    private void setToken(CustomHttpRequest customHttpRequest) {
        if(customHttpRequest.getToken() != null) customHttpRequest.getHttpHeaders().set("Authorization", "Bearer " + customHttpRequest.getToken());
    }

    private void setUrlEncodedBody(CustomHttpRequest customHttpRequest) {
        if(customHttpRequest.getBody() instanceof Client) {
            Map<String, String> map = ObjectMapperUtils.convertValue(customHttpRequest.getBody(), Map.class);
            if (customHttpRequest.getUrlEncodedBody() == null) {
                customHttpRequest.setUrlEncodedBody(new LinkedMultiValueMap<>());
            }
            map.forEach(customHttpRequest.getUrlEncodedBody()::add);
        }
    }

    private void setUrlEncodedBodyForLoginInSpotifyAPI() {

    }
}
