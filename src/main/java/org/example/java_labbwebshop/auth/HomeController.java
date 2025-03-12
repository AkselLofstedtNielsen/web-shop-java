package org.example.java_labbwebshop.auth;

import org.example.java_labbwebshop.category.CategoryService;
import org.example.java_labbwebshop.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/home")
    public String showHomePage(@RequestParam(value = "userId", required = false) Long userId,
                               @RequestParam(value = "categoryId", required = false) Long categoryId, Model model
    ) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("userId", userId);
        if (categoryId != null) {
            model.addAttribute("products", productService.getProductsByCategory(categoryId));
        } else {
            model.addAttribute("products", productService.getAllProducts());
        }
        return "home";
    }

}
