package org.example.java_labbwebshop.api.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.java_labbwebshop.api.model.DiscogsProperties;
import org.example.java_labbwebshop.api.model.DiscogsReleaseResponse;
import org.example.java_labbwebshop.api.model.DiscogsResult;
import org.example.java_labbwebshop.api.model.DiscogsSearchResponse;
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
        String url = discogsProperties.getSearchEndpoint() + "?q=" + UriUtils.encode(query, StandardCharsets.UTF_8) + "&type=release";

        DiscogsSearchResponse response = restClient.get()
                .uri(url)
                .retrieve()
                .body(DiscogsSearchResponse.class);

        if (response != null && response.results() != null) {
            return response.results();
        }
        return new ArrayList<>();
    }

    public DiscogsReleaseResponse getReleaseById(int id) {
        try {
            String url = discogsProperties.getReleaseEndpoint() + "/" + id;

            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(DiscogsReleaseResponse.class);
        } catch (Exception e) {
            log.error("Error fetching release with ID {}: {}", id, e.getMessage());
            return new DiscogsReleaseResponse(id, "Unknown Release " + id, List.of("Unknown"));
        }
    }
}