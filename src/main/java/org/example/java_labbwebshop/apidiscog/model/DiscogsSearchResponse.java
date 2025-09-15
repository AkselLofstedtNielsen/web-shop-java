package org.example.java_labbwebshop.apidiscog.model;

import java.util.List;

public record DiscogsSearchResponse(
        List<DiscogsSearchResultDto> results
) {}



