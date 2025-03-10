package org.example.java_labbwebshop.controllers;

import org.example.java_labbwebshop.service.CategoryService;
import org.example.java_labbwebshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam(value = "categoryId", required = false) Long categoryId, Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());

        if (categoryId != null) {
            model.addAttribute("products", productService.getProductsByCategory(categoryId));
        } else {
            model.addAttribute("products", productService.getAllProducts());
        }
        return "dashboard";
    }
}
