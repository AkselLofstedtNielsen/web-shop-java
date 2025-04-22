package org.example.java_labbwebshop.home;

import lombok.AllArgsConstructor;
import org.example.java_labbwebshop.discogsAPI.model.DiscogsResult;
import org.example.java_labbwebshop.discogsAPI.service.DiscogsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
public class HomeController {

    private DiscogsService discogsService;

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String genre,
            Model model) {

        // Get releases from Discogs API
        List<DiscogsResult> releases;

        if (search != null && !search.isEmpty()) {
            // Search by keyword
            releases = discogsService.searchReleases(search);
        } else if (genre != null && !genre.isEmpty()) {
            // Filter by genre
            releases = discogsService.searchReleases(genre).stream()
                    .filter(release -> release.genre() != null && 
                            release.genre().stream().anyMatch(g -> g.equalsIgnoreCase(genre)))
                    .collect(Collectors.toList());
        } else {
            // Default search for vinyl releases
            releases = discogsService.searchReleases("vinyl");
        }

        // Get all available genres from the releases
        List<String> genres = releases.stream()
                .filter(release -> release.genre() != null && !release.genre().isEmpty())
                .flatMap(release -> release.genre().stream())
                .distinct()
                .collect(Collectors.toList());

        model.addAttribute("releases", releases);
        model.addAttribute("genres", genres);
        model.addAttribute("search", search);
        model.addAttribute("selectedGenre", genre);

        return "home";
    }
}
