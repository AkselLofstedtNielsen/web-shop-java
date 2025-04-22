package org.example.java_labbwebshop.discogsAPI.model;

import java.util.List;

public record DiscogsReleaseResponse(
        int id,
        String title,
        List<String> genres
) {}
