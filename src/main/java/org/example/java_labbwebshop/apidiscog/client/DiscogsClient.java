package org.example.java_labbwebshop.apidiscog.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.java_labbwebshop.apidiscog.model.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriUtils;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiscogsClient {

    private final RestClient restClient;
    private final DiscogsProperties discogsProperties;

    public List<DiscogsResult> searchReleases(String query) {
        String url = discogsProperties.getSearchEndpoint()
                + "?q=" + UriUtils.encode(query, StandardCharsets.UTF_8)
                + "&type=release";

        DiscogsSearchResponse response = restClient.get()
                .uri(url)
                .retrieve()
                .body(DiscogsSearchResponse.class);

        if (response != null && response.results() != null) {
            return response.results().stream()
                    .map(this::mapToDiscogsResult)
                    .toList();
        }
        return new ArrayList<>();
    }

    public DiscogsReleaseResponse getReleaseById(int id) {
        try {
            String url = discogsProperties.getReleaseEndpoint() + "/" + id;

            DiscogsReleaseResponse response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(DiscogsReleaseResponse.class);

            if (response == null) {
                throw new RuntimeException("No release data returned for ID: " + id);
            }

            return response;
        } catch (Exception e) {
            log.error("Error fetching release with ID {}: {}", id, e.getMessage());
            return new DiscogsReleaseResponse(
                    id,
                    "Unknown Release " + id,
                    null,
                    List.of("Unknown"),
                    List.of(),
                    null,
                    null,
                    null
            );
        }
    }


    private DiscogsResult mapToDiscogsResult(DiscogsSearchResultDto dto) {
        // Splitta "Artist - Album" om möjligt
        String artist = null;
        String title = dto.title();
        if (title != null && title.contains(" - ")) {
            String[] parts = title.split(" - ", 2);
            artist = parts[0];
            title = parts.length > 1 ? parts[1] : title;
        }

        return new DiscogsResult(
                dto.id(),
                title,
                dto.year(),
                dto.genre(),
                dto.style(),
                dto.country(),
                dto.coverImage(),
                artist
        );
    }
}
