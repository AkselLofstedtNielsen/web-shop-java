package org.example.java_labbwebshop.home;

import lombok.RequiredArgsConstructor;
import org.example.java_labbwebshop.apidiscog.model.DiscogsResult;
import org.example.java_labbwebshop.apidiscog.service.DiscogsService;
import org.example.java_labbwebshop.home.dto.HomeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/home")
public class HomeRestController {

    private final DiscogsService discogsService;

    @GetMapping
    public ResponseEntity<HomeResponse> getHomeData(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String genre) {

        List<DiscogsResult> releases = discogsService.getFilteredReleases(search, genre);
        List<String> genres = discogsService.extractGenres(releases);

        return ResponseEntity.ok(new HomeResponse(releases, genres, search, genre));
    }
}
