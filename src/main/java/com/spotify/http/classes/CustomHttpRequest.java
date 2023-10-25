package com.spotify.http.classes;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

import java.util.List;

public class CustomHttpRequest {

    private HttpMethod method;
    private String url;
    private Object body;
    private String token;
    private HttpHeaders httpHeaders;
    private List<String> queryParams;
    private MultiValueMap<String, String> urlEncodedBody;

    public CustomHttpRequest(HttpMethod method, String url) {
        this.method = method;
        this.url = url;
    }

    public CustomHttpRequest(HttpMethod method, String url, HttpHeaders httpHeaders) {
        this.method = method;
        this.url = url;
        this.httpHeaders = httpHeaders;
    }

    public CustomHttpRequest(HttpMethod method, String url, String token) {
        this.method = method;
        this.url = url;
        this.token = token;
    }



    public CustomHttpRequest(HttpMethod method, String url, Object body) {
        this.method = method;
        this.url = url;
        this.body = body;
    }

    public CustomHttpRequest(HttpMethod method, String url, Object body, String token) {
        this.method = method;
        this.url = url;
        this.body = body;
        this.token = token;
    }

    public CustomHttpRequest(HttpMethod method, String url, Object body, String token, HttpHeaders httpHeaders) {
        this.method = method;
        this.url = url;
        this.body = body;
        this.token = token;
        this.httpHeaders = httpHeaders;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public List<String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(List<String> queryParams) {
        this.queryParams = queryParams;
    }

    public MultiValueMap<String, String> getUrlEncodedBody() {
        return urlEncodedBody;
    }

    public void setUrlEncodedBody(MultiValueMap<String, String> urlEncodedBody) {
        this.urlEncodedBody = urlEncodedBody;
    }
}
