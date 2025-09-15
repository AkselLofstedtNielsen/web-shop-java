package org.example.java_labbwebshop.order.controller;

import lombok.RequiredArgsConstructor;
import org.example.java_labbwebshop.order.model.Order;
import org.example.java_labbwebshop.order.service.OrderService;
import org.example.java_labbwebshop.user.User;
import org.example.java_labbwebshop.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;

    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderService.placeOrder(user);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping("/{orderId}/total")
    public ResponseEntity<BigDecimal> getOrderTotal(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(orderService.calculateTotalPrice(order));
    }
}
