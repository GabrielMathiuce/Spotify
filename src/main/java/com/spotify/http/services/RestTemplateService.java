package com.spotify.http.services;

import com.spotify.exceptions.MediaTypeNotSupportedException;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class RestTemplateService {

    private final RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    public RestTemplateService() {
        this.restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    }

    public ResponseEntity<String> createHttpRequest(CustomHttpRequest customHttpRequest, MediaType mediaType)  {
        return restTemplate.exchange(customHttpRequest.getUrl(), customHttpRequest.getMethod(), createHttpEntity(customHttpRequest, mediaType), String.class);
    }

    private UriComponentsBuilder createUriComponentsBuilder(CustomHttpRequest customHttpRequest) {
        return UriComponentsBuilder.fromUriString(customHttpRequest.getUrl());
    }

    private HttpEntity<Object> createHttpEntity(CustomHttpRequest customHttpRequest, MediaType mediaType) {

        setHeaders(customHttpRequest, mediaType);
        setToken(customHttpRequest);
        setQueryParams(customHttpRequest, createUriComponentsBuilder(customHttpRequest));
        setUrlEncodedBody(customHttpRequest);

        if(mediaType.equals(MediaType.APPLICATION_JSON)) return new HttpEntity<>(customHttpRequest.getBody(), customHttpRequest.getHttpHeaders());
        else if(mediaType.equals(MediaType.APPLICATION_FORM_URLENCODED)) return new HttpEntity<>(customHttpRequest.getUrlEncodedBody(), customHttpRequest.getHttpHeaders());
        else throw new MediaTypeNotSupportedException("The following media type is not supported for sending requests " + mediaType);
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
