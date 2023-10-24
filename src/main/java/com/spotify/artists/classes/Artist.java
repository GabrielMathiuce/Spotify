package com.spotify.artists.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Artist {

    @JsonProperty("external_urls")
    private Map<String, String> externalUrls;

    @JsonProperty("followers")
    private Follower followers;

    @JsonProperty("genres")
    private List<String> genres;

    @JsonProperty("href")
    private String href;

    @JsonProperty("id")
    private String id;

    @JsonProperty("images")
    private List<Image> images;

    @JsonProperty("name")
    private String name;

    @JsonProperty("popularity")
    private int popularity;

    @JsonProperty("type")
    private String type;

    @JsonProperty("uri")
    private String uri;
}
