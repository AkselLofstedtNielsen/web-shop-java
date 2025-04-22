package org.example.java_labbwebshop.discogs.service;

import lombok.RequiredArgsConstructor;
import org.example.java_labbwebshop.category.Category;
import org.example.java_labbwebshop.category.CategoryRepository;
import org.example.java_labbwebshop.discogs.model.DiscogsReleaseResponse;
import org.example.java_labbwebshop.discogs.model.DiscogsResult;
import org.example.java_labbwebshop.discogs.model.DiscogsSearchResponse;
import org.example.java_labbwebshop.product.Product;
import org.example.java_labbwebshop.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriUtils;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DiscogsService {

    private final RestClient restClient;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

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

    public Product saveReleaseAsProduct(int releaseId) {

        // Kontrollera om produkten redan finns i databasen
        List<Product> existingProducts = productRepository.findByNameContainingIgnoreCase("Discogs-" + releaseId);
        if (!existingProducts.isEmpty()) {
            for (Product product : existingProducts) {
                if (product.getName().startsWith("Discogs-" + releaseId + ": ")) {
                    return product;
                }
            }
        }

        // Hämta releasen från Discogs API
        DiscogsReleaseResponse release = getReleaseById(releaseId);
        if (release == null) {
            throw new RuntimeException("Release not found with ID: " + releaseId);
        }

        // Konvertera releasen till en Product och spara den
        return toProduct(release);
    }

    private Product toProduct(DiscogsReleaseResponse release) {
        double price = 100 + new Random().nextInt(150);

        String genreName = (release.genres() != null && !release.genres().isEmpty())
                ? release.genres().get(0)
                : "Unknown";

        Category category = categoryRepository.findByName(genreName)
                .orElseGet(() -> categoryRepository.save(new Category(genreName)));

        Product product = Product.builder()
                .name(release.title())
                .price(BigDecimal.valueOf(price))
                .category(category)
                .build();

        return productRepository.save(product);
    }
}
