package com.spotify.http.services;

import com.spotify.exceptions.MediaTypeNotSupportedException;
import com.spotify.http.classes.CustomHtttpRequest;
import com.spotify.utils.ObjectMapperUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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

    private final WebClient webClient = WebClient.create();

    public ResponseEntity<String> createJsonHttpRequest(CustomHtttpRequest customHtttpRequest)  {
        return createRequest(customHtttpRequest, MediaType.APPLICATION_JSON).toEntity(String.class).block();
    }

    public ResponseEntity<String> createFormUrlencodedHttpRequest(CustomHtttpRequest customHtttpRequest)  {
        return createRequest(customHtttpRequest, MediaType.APPLICATION_FORM_URLENCODED).toEntity(String.class).block();
    }

    public WebClient.ResponseSpec createRequest(CustomHtttpRequest customHtttpRequest, MediaType mediaType) {
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = setMethod(customHtttpRequest.getMethod());
        WebClient.RequestBodySpec bodySpec = setUrl(uriSpec, customHtttpRequest.getUrl());
        WebClient.RequestHeadersSpec<?> headersSpec = setBody(mediaType,customHtttpRequest.getBody(),bodySpec);
        headersSpec = setToken(headersSpec, customHtttpRequest.getToken());
        return setResponse(mediaType, headersSpec);
    }

    private  WebClient.UriSpec<WebClient.RequestBodySpec> setMethod(HttpMethod method) {
        return webClient.method(method);
    }

    private WebClient.RequestBodySpec setUrl(WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec, String url) {
        return uriSpec.uri(url);
    }

    private WebClient.RequestHeadersSpec<?> setToken(WebClient.RequestHeadersSpec<?> headersSpec ,String token){
        if (token != null) {
            headersSpec.header("Authorization", "Bearer " + token);
        }
        return headersSpec;
    }

    private WebClient.RequestHeadersSpec<?> setBody(MediaType mediaType, Object body, WebClient.RequestBodySpec bodySpec) {

        if (mediaType.equals(MediaType.APPLICATION_FORM_URLENCODED)) {
            if(body != null) return bodySpec.body(BodyInserters.fromFormData(convertPOJOToUrlencodedBody(body)));
        }

        else if (mediaType.equals(MediaType.APPLICATION_JSON)) {
            if(body != null) return bodySpec.bodyValue(body);
        }
        throw new MediaTypeNotSupportedException("The following media type is not supported for setting body " + mediaType);

    }

    private WebClient.ResponseSpec setResponse(MediaType mediaType, WebClient.RequestHeadersSpec<?> headersSpec) {
        if (mediaType.equals(MediaType.APPLICATION_FORM_URLENCODED)) {
            WebClient.ResponseSpec responseSpec = headersSpec
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .retrieve();

            return responseSpec;
        }

        else if (mediaType.equals(MediaType.APPLICATION_JSON)) {
            WebClient.ResponseSpec responseSpec = headersSpec
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .retrieve();

            return responseSpec;
        }
        throw new MediaTypeNotSupportedException("The following media type is not supported for sending requests " + mediaType);
    }

    private MultiValueMap<String, String> convertPOJOToUrlencodedBody(Object pojo) {
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.setAll(ObjectMapperUtils.convertValue(pojo, Map.class));
        return valueMap;
    }
}
