package org.example.java_labbwebshop.admin;

import lombok.RequiredArgsConstructor;
import org.example.java_labbwebshop.category.Category;
import org.example.java_labbwebshop.category.CategoryRepository;
import org.example.java_labbwebshop.order.OrderStatus;
import org.example.java_labbwebshop.order.model.Order;
import org.example.java_labbwebshop.order.repositories.OrderRepository;
import org.example.java_labbwebshop.product.Product;
import org.example.java_labbwebshop.product.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;

@RequiredArgsConstructor
@Controller
public class AdminController {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin";
    }

    @PostMapping("/admin/updateOrder")
    public String updateOrderStatus(@RequestParam("orderId") Long orderId,
                                    @RequestParam("newStatus") OrderStatus newStatus) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(newStatus);
        orderRepository.save(order);

        return "redirect:/admin";
    }

    @PostMapping("/admin/addProduct")
    public String addProduct(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("categoryId") Long categoryId
    ) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .name(name)
                .price(BigDecimal.valueOf(price))
                .category(category)
                .build();

        productRepository.save(product);

        return "redirect:/admin";
    }

}