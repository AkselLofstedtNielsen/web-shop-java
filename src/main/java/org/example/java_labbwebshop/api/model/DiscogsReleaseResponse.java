package org.example.java_labbwebshop.api.model;

import java.util.List;

public record DiscogsReleaseResponse(
        int id,
        String title,
        List<String> genres
) {}
