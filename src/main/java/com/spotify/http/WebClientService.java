package com.spotify.http;

import com.spotify.artists.classes.Artist;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientService {

    WebClient webClient = WebClient.create();

    public Artist createHttpRequest(HttpMethod method, String url, Object body, String token)  {
        WebClient.UriSpec<WebClient.RequestBodySpec> uriSpec = webClient.method(method);
        WebClient.RequestBodySpec bodySpec = uriSpec.uri(url);
        WebClient.RequestHeadersSpec<?> headersSpec = bodySpec.bodyValue(body);
        WebClient.ResponseSpec responseSpec = headersSpec.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + token)
                .retrieve();
        return headersSpec.retrieve().bodyToMono(Artist.class).block();
    }
}
