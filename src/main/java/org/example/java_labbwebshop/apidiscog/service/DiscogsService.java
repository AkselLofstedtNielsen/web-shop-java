package org.example.java_labbwebshop.apidiscog.service;

import lombok.RequiredArgsConstructor;
import org.example.java_labbwebshop.apidiscog.client.DiscogsClient;
import org.example.java_labbwebshop.apidiscog.model.DiscogsReleaseResponse;
import org.example.java_labbwebshop.apidiscog.model.DiscogsResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscogsService {

    private final DiscogsClient discogsClient;

    /**
     * Hämtar releases från Discogs API baserat på sökterm.
     */
    public List<DiscogsResult> searchReleases(String query) {
        return discogsClient.searchReleases(query);
    }

    /**
     * Hämtar en release från Discogs API baserat på ID.
     */
    public DiscogsReleaseResponse getReleaseById(int id) {
        return discogsClient.getReleaseById(id);
    }

    /**
     * Filtrerar releases baserat på sökterm eller genre.
     * Om inget anges returneras "vinyl"-sökning.
     */
    public List<DiscogsResult> getFilteredReleases(String search, String genre) {
        if (search != null && !search.isBlank()) {
            return searchReleases(search);
        } else if (genre != null && !genre.isBlank()) {
            return searchReleases(genre).stream()
                    .filter(release -> release.genre() != null &&
                            release.genre().stream().anyMatch(g -> g.equalsIgnoreCase(genre)))
                    .toList();
        } else {
            return searchReleases("vinyl");
        }
    }

    /**
     * Extraherar unika genres från en lista av releases.
     */
    public List<String> extractGenres(List<DiscogsResult> releases) {
        return releases.stream()
                .filter(release -> release.genre() != null && !release.genre().isEmpty())
                .flatMap(release -> release.genre().stream())
                .distinct()
                .toList();
    }
}
