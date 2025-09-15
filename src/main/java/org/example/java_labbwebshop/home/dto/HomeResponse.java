package org.example.java_labbwebshop.home.dto;

import org.example.java_labbwebshop.apidiscog.model.DiscogsResult;

import java.util.List;

public record HomeResponse(
        List<DiscogsResult> releases,
        List<String> genres,
        String search,
        String selectedGenre
) {}
