package com.spotify.http.classes;

import org.springframework.http.HttpMethod;

public class CustomHtttpRequest {

    private HttpMethod method;
    private String url;
    private Object body;
    private String token;

    public CustomHtttpRequest(HttpMethod method, String url) {
        this.method = method;
        this.url = url;
    }

    public CustomHtttpRequest(HttpMethod method, String url, String token) {
        this.method = method;
        this.url = url;
        this.token = token;
    }

    public CustomHtttpRequest(HttpMethod method, String url, Object body) {
        this.method = method;
        this.url = url;
        this.body = body;
    }

    public CustomHtttpRequest(HttpMethod method, String url, Object body, String token) {
        this.method = method;
        this.url = url;
        this.body = body;
        this.token = token;
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
}
