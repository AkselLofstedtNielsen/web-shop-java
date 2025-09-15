package org.example.java_labbwebshop.apidiscog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record DiscogsReleaseResponse(
        int id,
        String title,
        Integer year,
        List<String> genres,
        List<String> styles,
        String country,
        @JsonProperty("cover_image") String coverImage,
        @JsonProperty("artists_sort") String artist
) {}
