package org.example.java_labbwebshop.home;

import lombok.RequiredArgsConstructor;
import org.example.java_labbwebshop.api.model.DiscogsResult;
import org.example.java_labbwebshop.api.service.DiscogsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final DiscogsService discogsService;

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String genre,
            Model model) {

        List<DiscogsResult> releases;

        if (search != null && !search.isEmpty()) {
            releases = discogsService.searchReleases(search);
        } else if (genre != null && !genre.isEmpty()) {
            releases = discogsService.searchReleases(genre).stream()
                    .filter(release -> release.genre() != null && 
                            release.genre().stream().anyMatch(g -> g.equalsIgnoreCase(genre)))
                    .collect(Collectors.toList());
        } else {
            releases = discogsService.searchReleases("vinyl");
        }

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
