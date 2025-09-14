package org.example.java_labbwebshop.api.service;

import lombok.RequiredArgsConstructor;
import org.example.java_labbwebshop.category.Category;
import org.example.java_labbwebshop.category.CategoryRepository;
import org.example.java_labbwebshop.api.client.DiscogsClient;
import org.example.java_labbwebshop.api.model.DiscogsReleaseResponse;
import org.example.java_labbwebshop.api.model.DiscogsResult;
import org.example.java_labbwebshop.product.Product;
import org.example.java_labbwebshop.product.ProductRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DiscogsService {

    private final DiscogsClient discogsClient;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<DiscogsResult> searchReleases(String query) {
        return discogsClient.searchReleases(query);
    }

    public DiscogsReleaseResponse getReleaseById(int id) {
        return discogsClient.getReleaseById(id);
    }

    public Product saveReleaseAsProduct(int releaseId) {
        // Get the release from Discogs API
        DiscogsReleaseResponse release = getReleaseById(releaseId);
        if (release == null) {
            throw new RuntimeException("Release not found with ID: " + releaseId);
        }

        // Check if the product already exists in the database by title
        List<Product> existingProducts = productRepository.findByNameContainingIgnoreCase(release.title());
        if (!existingProducts.isEmpty()) {
            // Return the first matching product
            return existingProducts.get(0);
        }

        // Convert the release to a Product and save it
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
