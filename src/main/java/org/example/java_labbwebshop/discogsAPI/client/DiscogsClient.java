package org.example.java_labbwebshop.discogsAPI.client;

import lombok.RequiredArgsConstructor;
import org.example.java_labbwebshop.discogsAPI.model.DiscogsReleaseResponse;
import org.example.java_labbwebshop.discogsAPI.model.DiscogsResult;
import org.example.java_labbwebshop.discogsAPI.model.DiscogsSearchResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Client for interacting with the Discogs API.
 * This class handles all direct API calls to Discogs.
 */
@Component
@RequiredArgsConstructor
public class DiscogsClient {

    private final RestClient restClient;

    /**
     * Search for releases in the Discogs database.
     *
     * @param query the search query
     * @return a list of DiscogsResult objects
     */
    public List<DiscogsResult> searchReleases(String query) {
        String url = "/database/search?q=" + UriUtils.encode(query, StandardCharsets.UTF_8) + "&type=release";

        DiscogsSearchResponse response = restClient.get()
                .uri(url)
                .retrieve()
                .body(DiscogsSearchResponse.class);

        if (response != null && response.results() != null) {
            return response.results();
        }

        return new ArrayList<>();
    }

    /**
     * Get detailed information about a specific release.
     *
     * @param id the release ID
     * @return a DiscogsReleaseResponse object
     */
    public DiscogsReleaseResponse getReleaseById(int id) {
        try {
            String url = "/releases/" + id;

            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(DiscogsReleaseResponse.class);
        } catch (Exception e) {
            System.err.println("Error fetching release with ID " + id + ": " + e.getMessage());
            return new DiscogsReleaseResponse(id, "Unknown Release " + id, List.of("Unknown"));
        }
    }
}