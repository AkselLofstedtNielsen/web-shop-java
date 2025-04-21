package org.example.java_labbwebshop.order.controller;

import lombok.AllArgsConstructor;
import org.example.java_labbwebshop.order.model.Order;
import org.example.java_labbwebshop.order.service.OrderService;
import org.example.java_labbwebshop.user.User;
import org.example.java_labbwebshop.user.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.math.BigDecimal;

@AllArgsConstructor
@Controller
public class OrderController {

    private OrderService orderService;

    private UserRepository userRepository;

    @PostMapping("/order/place")
    public String placeOrder(@AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            Order order = orderService.placeOrder(user);
            return "redirect:/order/confirmation?orderId=" + order.getId();
        } catch (RuntimeException e) {
            return "redirect:/cart?error=" + e.getMessage();
        }
    }


    @GetMapping("/order/confirmation")
    public String showConfirmation(@RequestParam("orderId") Long orderId, Model model) {
        try {
            Order order = orderService.getOrderById(orderId);
            BigDecimal totalPrice = orderService.calculateTotalPrice(order);

            model.addAttribute("order", order);
            model.addAttribute("totalPrice", totalPrice);

            return "orderconfirmation";
        } catch (RuntimeException e) {
            return "redirect:/cart?error=orderNotFound";
        }
    }


}
