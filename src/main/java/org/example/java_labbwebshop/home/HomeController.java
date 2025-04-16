package org.example.java_labbwebshop.home;

import org.example.java_labbwebshop.category.CategoryService;
import org.example.java_labbwebshop.product.ProductService;
import org.example.java_labbwebshop.user.SessionUser;
import org.example.java_labbwebshop.user.User;
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

    @Autowired
    private SessionUser sessionUser;

    @GetMapping("/home")
    public String showHomePage(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "search", required = false) String search,
            Model model
    ) {
        User user = sessionUser.getUser();
        model.addAttribute("userId", user != null ? user.getId() : null);
        model.addAttribute("categories", categoryService.getAllCategories());

        if (search != null && !search.isEmpty()) {
            model.addAttribute("products", productService.searchProducts(search));
        } else if (categoryId != null) {
            model.addAttribute("products", productService.getProductsByCategory(categoryId));
        } else {
            model.addAttribute("products", productService.getAllProducts());
        }
        return "home";
    }
}
