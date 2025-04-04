package org.example.java_labbwebshop.admin;

import org.example.java_labbwebshop.order.OrderStatus;
import org.example.java_labbwebshop.order.model.Order;
import org.example.java_labbwebshop.order.repositories.OrderRepository;
import org.example.java_labbwebshop.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/admin")
    public String showAdminPage(
            @RequestParam(value = "userId", required = false) Long userId,
            Model model
    ) {
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("orderStatuses", OrderStatus.values());

        return "admin";
    }

    @PostMapping("/admin/updateOrder")
    public String updateOrderStatus(
            @RequestParam("orderId") Long orderId,
            @RequestParam("newStatus") OrderStatus newStatus
    ) {

         Optional<Order> orderOpt = orderRepository.findById(orderId);
         if (orderOpt.isPresent()) {
             Order order = orderOpt.get();
             order.setStatus(newStatus);
             orderRepository.save(order);
         }

        return "redirect:/admin";
    }

    // Add method for handling new product creation
}