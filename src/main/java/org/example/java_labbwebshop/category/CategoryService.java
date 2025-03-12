package org.example.java_labbwebshop.category;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @PostConstruct
    @Transactional
    public void mockCategories() {
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category("Rock"));
            categoryRepository.save(new Category("Pop"));
            categoryRepository.save(new Category("Punk"));
        }
    }

}
