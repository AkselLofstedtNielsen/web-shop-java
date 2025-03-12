package org.example.java_labbwebshop.product;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.example.java_labbwebshop.category.Category;
import org.example.java_labbwebshop.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @PostConstruct
    @Transactional
    public void mockProducts() {
        if (productRepository.count() == 0) {

            Category rockCategory = categoryRepository.findByName("Rock").orElse(null);
            Category popCategory = categoryRepository.findByName("Pop").orElse(null);
            Category punkCategory = categoryRepository.findByName("Punk").orElse(null);

            if (rockCategory != null) {
                productRepository.save(new Product("Nirvana - Nevermind", 210, rockCategory));
                productRepository.save(new Product("AC/DC - Back in Black", 249.0, rockCategory));
            }

            if (popCategory != null) {
                productRepository.save(new Product("Michael Jackson - Thriller", 199.0, popCategory));
                productRepository.save(new Product("Madonna - Like a Virgin", 179.0, popCategory));
            }

            // Lägg till Punk-produkter
            if (punkCategory != null) {
                productRepository.save(new Product("The Ramones - Ramones", 189.0, punkCategory));
                productRepository.save(new Product("Green Day - Dookie", 199.0, punkCategory));
            }
        }
    }
}
